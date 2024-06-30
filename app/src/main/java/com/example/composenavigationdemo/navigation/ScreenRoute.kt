package com.example.composenavigationdemo.navigation

sealed class ScreenRoute(val route: String) {
    data object Home : ScreenRoute("home")
    data object Second : ScreenRoute("second")
    data object Third : ScreenRoute("third")
    data object Last : ScreenRoute("last")
    data object Material2 : ScreenRoute("material2")
    data object Material3 : ScreenRoute("material3")
}

val BASE_SCREEN_ROUTE = ScreenRoute.Home.route