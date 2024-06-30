package com.example.composenavigationdemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenavigationdemo.ui.screen.HomeScreen
import com.example.composenavigationdemo.ui.screen.LastScreen
import com.example.composenavigationdemo.ui.screen.Material2Screen
import com.example.composenavigationdemo.ui.screen.Material3Screen
import com.example.composenavigationdemo.ui.screen.SecondScreen
import com.example.composenavigationdemo.ui.screen.ThirdScreen
import com.example.composenavigationdemo.viewmodel.ColorViewModel

@Composable
fun DemoNavHost(viewModel: ColorViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(ScreenRoute.Home.route) {
            HomeScreen(navigateTo = navController::navigate)
        }
        composable(ScreenRoute.Second.route) {
            SecondScreen()
        }
        composable(ScreenRoute.Third.route) {
            ThirdScreen()
        }
        composable(ScreenRoute.Last.route) {
            LastScreen()
        }
        composable(ScreenRoute.Material2.route) {
            Material2Screen(
                navigateTo = navController::navigateSingleStack,
                viewModel = viewModel
            )
        }
        composable(ScreenRoute.Material3.route) {
            Material3Screen(
                navigateTo = navController::navigateSingleStack,
                viewModel = viewModel
            )
        }
    }
}

fun NavHostController.navigateSingleStack(route: String) {
    navigate(BASE_SCREEN_ROUTE) {
        popUpTo(BASE_SCREEN_ROUTE) {
            inclusive = true
        }
    }

    navigate(route)
}
