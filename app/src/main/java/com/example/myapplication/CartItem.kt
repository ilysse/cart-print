package com.example.myapplication

data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int = 1
)
