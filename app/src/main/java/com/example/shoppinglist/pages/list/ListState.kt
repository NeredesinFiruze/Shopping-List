package com.example.shoppinglist.pages.list

import com.example.shoppinglist.data.model.DataList

data class ListState(
    val listName: String = "",
    val showList: List<DataList> = emptyList(),
    val navSetup: Boolean = false
)
