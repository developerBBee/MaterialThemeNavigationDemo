package com.example.composenavigationdemo.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composenavigationdemo.navigation.ScreenRouter
import com.example.composenavigationdemo.ui.component.BaseDialog
import com.example.composenavigationdemo.ui.component.RgbColorPicker
import com.example.composenavigationdemo.ui.theme.Material3Theme
import com.example.composenavigationdemo.ui.theme.generate
import com.example.composenavigationdemo.ui.theme.shouldLight
import com.example.composenavigationdemo.viewmodel.ColorViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3Screen(
    navigateTo: (ScreenRouter) -> Unit,
    viewModel: ColorViewModel,
) {
    val color by viewModel.color.collectAsState()
    val isDark by viewModel.darkMode.collectAsState()

    val colorScheme = generate(primaryColor = color, useLight = !isDark)

    var showDialog by remember { mutableStateOf(false) }

    var colorValue = colorScheme.primary

    Material3Theme(colorScheme = colorScheme) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Material3") },
                    actions = {
                        Text(text = "Light")
                        Switch(
                            checked = isDark,
                            onCheckedChange = { viewModel.toggleMode(dark = it) }
                        )
                        Text(text = "Dark")
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                Text(
                    modifier = Modifier.absoluteOffset(x = 60.dp, y = 16.dp),
                    text = "<- Primary container color?"
                )
            },
            floatingActionButtonPosition = FabPosition.Start
        ) { paddingValues ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                Material3Contents(
                    modifier = Modifier.fillMaxSize(),
                    onButtonClick = { navigateTo(ScreenRouter.Material2) },
                )

                if (showDialog) {
                    BaseDialog(
                        negativeText = "Cancel",
                        positiveText = "OK",
                        onNegativeClick = {
                            showDialog = false
                            colorValue = colorScheme.primary
                        },
                        onPositiveClick = {
                            showDialog = false
                            viewModel.setColor(color = colorValue)
                            generate(primaryColor = colorValue)
                                .also { viewModel.toggleMode(!it.shouldLight) }
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = "Choice Brand Color")

                            RgbColorPicker(
                                value = colorValue,
                                onColorChanged = { colorValue = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun Material3Contents(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var buttonEnable by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Material3Contents
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Text On Surface"
                )
            }
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(80.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp),
            enabled = buttonEnable,
            onClick = {
                buttonEnable = false
                scope.launch {
                    delay(2000L)
                    buttonEnable = true
                }
            }
        ) {
            val text = if (buttonEnable) "Enabled" else "Disabled"
            Text(
                textAlign = TextAlign.Center,
                text =  "Text On OutlinedButton\n\n$text"
            )
        }

        Card(modifier = Modifier.clickable { showDialog = true }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Text On Card\n\nShow Dialog"
                )
            }
        }

        Button(
            enabled = buttonEnable,
            onClick = onButtonClick
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Text On Button\n\nTransition to Material2"
                )
            }
        }

        if (showDialog) {
            BaseDialog(
                positiveText = "OK",
                negativeText = "Cancel",
                onPositiveClick = { showDialog = false },
                onNegativeClick = { showDialog = false }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(48.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val text = MaterialTheme.colorScheme.run {
                        "primary=${primary.toArgb().toHexString()}\n" +
                                "onPrimary=${onPrimary.toArgb().toHexString()}\n" +
                                "primaryContainer=${primaryContainer.toArgb().toHexString()}\n" +
                                "onPrimaryContainer=${onPrimaryContainer.toArgb().toHexString()}\n" +
                                "inversePrimary=${inversePrimary.toArgb().toHexString()}\n" +
                                "secondary=${secondary.toArgb().toHexString()}\n" +
                                "onSecondary=${onSecondary.toArgb().toHexString()}\n" +
                                "secondaryContainer=${secondaryContainer.toArgb().toHexString()}\n" +
                                "onSecondaryContainer=${onSecondaryContainer.toArgb().toHexString()}\n" +
                                "tertiary=${tertiary.toArgb().toHexString()}\n" +
                                "onTertiary=${onTertiary.toArgb().toHexString()}\n" +
                                "tertiaryContainer=${tertiaryContainer.toArgb().toHexString()}\n" +
                                "onTertiaryContainer=${onTertiaryContainer.toArgb().toHexString()}\n" +
                                "background=${background.toArgb().toHexString()}\n" +
                                "onBackground=${onBackground.toArgb().toHexString()}\n" +
                                "surface=${surface.toArgb().toHexString()}\n" +
                                "onSurface=${onSurface.toArgb().toHexString()}\n" +
                                "surfaceVariant=${surfaceVariant.toArgb().toHexString()}\n" +
                                "onSurfaceVariant=${onSurfaceVariant.toArgb().toHexString()}\n" +
                                "surfaceTint=${surfaceTint.toArgb().toHexString()}\n" +
                                "inverseSurface=${inverseSurface.toArgb().toHexString()}\n" +
                                "inverseOnSurface=${inverseOnSurface.toArgb().toHexString()}\n" +
                                "error=${error.toArgb().toHexString()}\n" +
                                "onError=${onError.toArgb().toHexString()}\n" +
                                "errorContainer=${errorContainer.toArgb().toHexString()}\n" +
                                "onErrorContainer=${onErrorContainer.toArgb().toHexString()}\n" +
                                "outline=${outline.toArgb().toHexString()}\n" +
                                "outlineVariant=${outlineVariant.toArgb().toHexString()}\n" +
                                "scrim=${scrim.toArgb().toHexString()}\n" +
                                "surfaceBright=${surfaceBright.toArgb().toHexString()}\n" +
                                "surfaceDim=${surfaceDim.toArgb().toHexString()}\n" +
                                "surfaceContainer=${surfaceContainer.toArgb().toHexString()}\n" +
                                "surfaceContainerHigh=${surfaceContainerHigh.toArgb().toHexString()}\n" +
                                "surfaceContainerHighest=${surfaceContainerHighest.toArgb().toHexString()}\n" +
                                "surfaceContainerLow=${surfaceContainerLow.toArgb().toHexString()}\n" +
                                "surfaceContainerLowest=${surfaceContainerLowest.toArgb().toHexString()}"
                    }
                    Text(
                        text = "Text On Dialog"
                    )
                    Text(
                        text = text
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun Material3ContentsLightPreview() {
//    Material3Screen(darkTheme = false, navigateTo = {})
//}
//@Preview
//@Composable
//fun Material3ContentsDarkPreview() {
//    Material3Screen(darkTheme = true, navigateTo = {})
//}