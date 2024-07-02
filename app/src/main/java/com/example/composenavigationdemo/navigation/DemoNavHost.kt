package com.example.composenavigationdemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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
        composable(ScreenRouter.Home.route) {
            HomeScreen(navigateTo = navController::navigate)
        }
        composable(ScreenRouter.Second.route) {
            SecondScreen()
        }
        composable(ScreenRouter.Third.route) {
            ThirdScreen()
        }
        composable(ScreenRouter.Last.route) {
            LastScreen()
        }
        composable(ScreenRouter.Material2.route) {
            Material2Screen(
                navigateTo = navController::navigateSingleStack,
                viewModel = viewModel
            )
        }
        composable(ScreenRouter.Material3.route) {
            Material3Screen(
                navigateTo = navController::navigateSingleStack,
                viewModel = viewModel
            )
        }
    }
}

fun NavController.navigateSingleStack(router: ScreenRouter) {
    navigate(router.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    navigate(router.route)
}
