package com.example.shoppinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shoppinglist.pages.add_list.AddList
import com.example.shoppinglist.pages.add_list.AddListItem
import com.example.shoppinglist.pages.edit_item.EditItem
import com.example.shoppinglist.pages.list.ListScreen
import com.example.shoppinglist.pages.settings.Settings

@Composable
fun NavListSetup(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.List.route
    ){
        composable(Screen.List.route){
            ListScreen(navHostController)
        }
        composable(Screen.AddList.route){
            AddList(navHostController)
        }
        composable(
            route = Screen.AddListItem.route,
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                },
                navArgument("color"){
                    type = NavType.IntType
                }
            )
        ){
            val name = it.arguments?.getString("name").toString()
            val color = it.arguments?.getInt("color")

            AddListItem(
                navController = navHostController,
                name = name,
                color = color ?: 0
            )
        }
        composable(
            route = Screen.EditItem.route,
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                }
            )
        ){
            EditItem()
        }
    }
}