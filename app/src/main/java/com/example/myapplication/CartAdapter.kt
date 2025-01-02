package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCartBinding

class CartAdapter(
    private val items: List<CartItem>,
    private val onQuantityChange: (CartItem, Boolean) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            itemNameText.text = item.name
            itemPriceText.text = String.format("$%.2f", item.price)
            quantityText.text = item.quantity.toString()

            incrementButton.setOnClickListener { onQuantityChange(item, true) }
            decrementButton.setOnClickListener { onQuantityChange(item, false) }
        }
    }

    override fun getItemCount() = items.size

}