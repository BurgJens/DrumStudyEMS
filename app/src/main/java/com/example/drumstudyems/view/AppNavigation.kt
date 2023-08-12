package com.example.drumstudyems.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.*
import com.example.drumstudyems.Greeting


sealed class NavScreen(val route : String){
    object Start : NavScreen("start")
    object First : NavScreen("first")
}

@Composable
fun AppNavigation(
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.Start.route){
        composable(
            route = NavScreen.Start.route
        ){
            ScreenStartHandler {
                {}
            }
        }
    }
}