package com.example.shoppinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shoppinglist.pages.settings.Settings

@Composable
fun NavSettingSetup(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Settings.route
    ){
        composable(Screen.Settings.route){
            Settings()
        }
    }
}