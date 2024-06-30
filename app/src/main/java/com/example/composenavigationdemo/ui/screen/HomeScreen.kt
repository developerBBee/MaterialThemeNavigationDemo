package com.example.composenavigationdemo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenavigationdemo.navigation.ScreenRoute

@Composable
fun HomeScreen(
    navigateTo: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Home Screen")
        TextButton(onClick = { navigateTo(ScreenRoute.Second.route) }) {
            Text(text = "Go to Second Screen")
        }
        TextButton(onClick = { navigateTo(ScreenRoute.Third.route) }) {
            Text(text = "Go to Third Screen")
        }
        TextButton(onClick = { navigateTo(ScreenRoute.Last.route) }) {
            Text(text = "Go to Last Screen")
        }
        TextButton(onClick = { navigateTo(ScreenRoute.Material2.route) }) {
            Text(text = "Go to Material2 Screen")
        }
        TextButton(onClick = { navigateTo(ScreenRoute.Material3.route) }) {
            Text(text = "Go to Material3 Screen")
        }
    }
}