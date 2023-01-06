package com.example.shoppinglist.pages.add_list

import com.example.shoppinglist.data.model.Groceries

data class AddListState(
    var color: Int = -1,
    var name: String = "",
    var searchItem: String = "",
    val list: ArrayList<Groceries> = arrayListOf()
)
