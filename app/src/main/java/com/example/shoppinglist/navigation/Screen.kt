package com.example.shoppinglist.navigation

sealed class Screen(val route: String){
    object List: Screen("list_screen")
    object AddList: Screen("add_list_screen")
    object Settings: Screen("settings_screen")
    object EditItem: Screen("new_item_screen/{id}"){
        fun getId(id: Int): String{
            return "new_item_screen/$id"
        }
    }
    object AddListItem: Screen("add_list_item_screen/{name}/{color}"){
        fun getInfo(name: String, color: Int): String{
            return "add_list_item_screen/$name/$color"
        }
    }
}