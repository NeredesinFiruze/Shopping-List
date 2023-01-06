package com.example.shoppinglist.pages.edit_item

import com.example.shoppinglist.data.model.Groceries

data class EditState(
    val name: String = "",
    val list: List<Groceries> = emptyList(),
    val color: Int = -1,
    val isCheck: List<Boolean> = emptyList()
)
