package com.example.shoppinglist.pages.edit_item

sealed class EditEvent{
    data class ChangeColor(val color: Int): EditEvent()
    object Save: EditEvent()
}
