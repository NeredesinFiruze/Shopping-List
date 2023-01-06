package com.example.shoppinglist.data.model

data class Groceries(
    val nameOfGroceries: String,
    val type: String,
    var count: Int = 0,
    var isCheck: Boolean = false
)