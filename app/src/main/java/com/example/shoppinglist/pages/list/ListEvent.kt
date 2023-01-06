package com.example.shoppinglist.pages.list

import com.example.shoppinglist.data.model.DataList

sealed class ListEvent{
    data class SearchList(val query: String): ListEvent()
    data class RemoveList(val list: DataList): ListEvent()
    object NavSetup: ListEvent()
    object ShowList: ListEvent()
}
