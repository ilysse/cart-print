package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemCartBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()

        // Add sample items
        addSampleItems()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(cartItems) { item, increment ->
            updateQuantity(item, increment)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupButtons() {
        binding.printButton.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            printInvoice()
        }

        binding.addItemButton.setOnClickListener {
            // In a real app, you would show a dialog or navigate to an item selection screen
            val newItem = CartItem(
                id = cartItems.size + 1,
                name = "Item ${cartItems.size + 1}",
                price = (10..50).random().toDouble()
            )
            cartItems.add(newItem)
            adapter.notifyItemInserted(cartItems.size - 1)
            updateTotal()
        }
    }

    private fun addSampleItems() {
        cartItems.addAll(
            listOf(
                CartItem(1, "Product A", 19.99),
                CartItem(2, "Product B", 29.99),
                CartItem(3, "Product C", 39.99)
            )
        )
        adapter.notifyDataSetChanged()
        updateTotal()
    }

    private fun updateQuantity(item: CartItem, increment: Boolean) {
        val index = cartItems.indexOf(item)
        if (index != -1) {
            if (increment) {
                cartItems[index].quantity++
            } else if (cartItems[index].quantity > 1) {
                cartItems[index].quantity--
            }
            adapter.notifyItemChanged(index)
            updateTotal()
        }
    }

    private fun updateTotal() {
        val total = cartItems.sumOf { it.price * it.quantity }
        binding.totalTextView.text = String.format("Total: $%.2f", total)
    }

    private fun printInvoice() {
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    
                    val socket = Socket("192.168.10.200", 9100)
                    val writer = OutputStreamWriter(socket.getOutputStream())

                    // Generate invoice content
                    val invoice = generateInvoice()

                    // Send to printer
                    writer.write(invoice)
                    writer.flush()
                    socket.close()
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Invoice printed successfully!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Printing failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun generateInvoice(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        return buildString {
            appendLine("\u001B@")  // Initialize printer
            appendLine("\u001BE1") // Set font style
            appendLine("\u001Ba1") // Center alignment

            appendLine("ilyas store")
            appendLine("CMC TAMESNA")
            appendLine("rabat, rabat 123")
            appendLine("Tel: (+212) 651463220")
            appendLine("\n")

            appendLine("INVOICE")
            appendLine(currentDate)
            appendLine("-".repeat(32))

            appendLine("\u001Ba0") // Left alignment
            cartItems.forEach { item ->
                appendLine("${item.name}")
                appendLine("${item.quantity} x $${String.format("%.2f", item.price)} = $${String.format("%.2f", item.quantity * item.price)}")
            }

            appendLine("-".repeat(32))
            appendLine("Total: $${String.format("%.2f", cartItems.sumOf { it.price * it.quantity })}")

            // Ensuring no extra space at the bottom
             // Cut paper
        }
    }

}
