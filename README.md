# cart-print

Project Overview
This Android application simulates a simple shopping cart system. It allows users to add items to their cart, adjust item quantities, and print an invoice for their purchase. The application includes an invoice generation feature that sends the invoice data to a thermal printer over a network using a socket connection. The app is built using Kotlin and follows modern Android architecture components such as RecyclerView for displaying cart items and Kotlin coroutines for handling network operations.

Features
Shopping Cart: Users can add items to a cart, view them, and adjust quantities.
Invoice Generation: The app can generate an invoice with details about the items in the cart, including their names, quantities, and total price.
Thermal Printer Integration: The app is designed to send the generated invoice to a thermal printer over the network using a socket connection.
UI Elements:
RecyclerView to display the list of cart items.
Buttons for adding items to the cart and printing the invoice.
Total Calculation: The total price of the cart items is dynamically calculated and displayed.
Key Components
MainActivity: This is the main entry point of the application. It contains logic to manage the cart, update item quantities, and handle the printing of invoices.
CartAdapter: This adapter binds the cart items to the RecyclerView and allows users to modify item quantities.
CartItem: A simple data class representing an item in the shopping cart, containing attributes like name, price, and quantity.
Socket Communication: The app communicates with a thermal printer via a network socket (IP address 192.168.10.200 and port 9100).
Invoice Generation: The invoice is formatted in ESC/POS (a command language used by many thermal printers), allowing text alignment and printing of item details in a structured format.
Technologies Used
Kotlin: The main programming language used for building the app.
Android SDK: Core Android components like RecyclerView, ViewBinding, and LifecycleScope for coroutine support.
Coroutines: Used for handling asynchronous tasks like printing the invoice to avoid blocking the main thread.
Socket Communication: Used to send data to the printer over the network.
How to Run the Application
Clone the Repository:

bash
Copier le code
git clone https://github.com/ilysse/cart-print/
cd cart-print
Open the Project: Open the project in Android Studio.

Modify Printer IP: Ensure the printer's IP address is correctly configured in the printInvoice() method. Update the following line with your printer's IP:

kotlin
Copier le code
val socket = Socket("192.168.10.200", 9100)
Build and Run: Connect your Android device or use an emulator and run the application.

Screenshots
(If applicable, add screenshots of your app's user interface)

Dependencies
Kotlin Coroutines: For asynchronous tasks like printing.
RecyclerView: For displaying cart items.
ViewBinding: To access and bind views in a type-safe manner.
License
This project is licensed under the MIT License - see the LICENSE file for details.

This description provides an overview of your app's functionality, how it works, and how to get started with it. Adjust any part of it to fit your specific project and use case.
