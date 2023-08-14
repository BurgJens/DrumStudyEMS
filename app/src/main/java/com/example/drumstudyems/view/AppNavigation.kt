package com.example.drumstudyems.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.*


sealed class NavScreen(val route : String){
    object Start : NavScreen("start")
    object Drum : NavScreen("drum")
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
                navController.navigate(NavScreen.Drum.route)
            }
        }
        composable(
            route = NavScreen.Drum.route
        ){
            ScreenDrumHero()
        }
    }
}