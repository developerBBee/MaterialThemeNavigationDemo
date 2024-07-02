package com.example.composenavigationdemo.navigation

sealed class ScreenRouter(val route: String) {
    data object Home : ScreenRouter("home")
    data object Second : ScreenRouter("second")
    data object Third : ScreenRouter("third")
    data object Last : ScreenRouter("last")
    data object Material2 : ScreenRouter("material2")
    data object Material3 : ScreenRouter("material3")
}
