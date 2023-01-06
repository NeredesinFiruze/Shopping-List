package com.example.shoppinglist.data.model

import androidx.compose.ui.graphics.Color
import com.example.shoppinglist.ui.theme.Pink50
import com.example.shoppinglist.ui.theme.Pink500
import com.example.shoppinglist.ui.theme.Pink5000
import com.example.shoppinglist.ui.theme.Pink50000

object ListOfGroceries {

    val listOfGroceries: ArrayList<Groceries> = arrayListOf(
        Groceries(
            nameOfGroceries = "Sucuk",
            type = "kg"
        ),
        Groceries(
            nameOfGroceries = "Süt",
            type = "litre"
        ),
        Groceries(
            nameOfGroceries = "Kola",
            type = "litre"
        ),
        Groceries(
            nameOfGroceries = "Patates",
            type = "kg"
        ),
        Groceries(
            nameOfGroceries = "Pirinç",
            type = "kg"
        ),
        Groceries(
            nameOfGroceries = "Ihlamur",
            type = "kg"
        )
    )

    val listOfColor: List<Color> = listOf(Pink50, Pink500, Pink5000, Pink50000)
}