package com.example.shoppinglist.pages.add_list

sealed class AddListEvent {
    data class ChangeColor(val color: Int): AddListEvent()
    data class AddName(val name: String): AddListEvent()
    data class AddItem(val item: String): AddListEvent()
    data class AddToList(val name: String, val count: Int): AddListEvent()
    data class ReplaceItem(val name: String, val count: Int): AddListEvent()
    object SaveToDatabase: AddListEvent()
}