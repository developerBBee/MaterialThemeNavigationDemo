package com.example.composenavigationdemo.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composenavigationdemo.navigation.ScreenRoute
import com.example.composenavigationdemo.ui.component2.BaseDialog
import com.example.composenavigationdemo.ui.component2.RgbColorPicker
import com.example.composenavigationdemo.ui.theme2.Material2Theme
import com.example.composenavigationdemo.ui.theme2.Orange500
import com.example.composenavigationdemo.ui.theme2.generate
import com.example.composenavigationdemo.viewmodel.ColorViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Material2Screen(
    darkTheme: Boolean = false,
    navigateTo: (String) -> Unit,
    viewModel: ColorViewModel,
) {
    val color by viewModel.color.collectAsState(initial = Orange500)
    val isDark by viewModel.darkMode.collectAsState(initial = darkTheme)

    var darkSwitchOn: Boolean? by remember { mutableStateOf(null) }

    val colorPalette = (darkSwitchOn?.let {
        generate(primaryColor = color, useLight = !it)
    } ?: generate(primaryColor = color))
        .also { viewModel.toggleMode(!it.isLight) }

    var showDialog by remember { mutableStateOf(false) }

    var colorValue =colorPalette.primary

    Material2Theme(colorPalette = colorPalette) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    Text(
                        text = "Material2",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Light")
                    Switch(
                        checked = isDark,
                        onCheckedChange = {
                            darkSwitchOn = it
                            viewModel.toggleMode(dark = it)
                            viewModel.setColor(color = colorPalette.primary)
                        }
                    )
                    Text(text = "Dark")
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                Text(
                    modifier = Modifier.absoluteOffset(x = 60.dp, y = 16.dp),
                    text = "<- Secondary color"
                )
            },
            floatingActionButtonPosition = FabPosition.Start
        ) { paddingValues ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                Material2Contents(
                    modifier = Modifier.fillMaxSize(),
                    onButtonClick = { navigateTo(ScreenRoute.Material3.route) },
                )

                if (showDialog) {
                    BaseDialog(
                        negativeText = "Cancel",
                        positiveText = "OK",
                        onNegativeClick = {
                            showDialog = false
                            colorValue = colorPalette.primary
                        },
                        onPositiveClick = {
                            showDialog = false
                            darkSwitchOn = null
                            viewModel.setColor(colorValue)
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
fun Material2Contents(
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
        // Material2Contents
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
            border = ButtonDefaults.outlinedBorder.copy(width = 2.dp),
            enabled = buttonEnable,
            onClick = {
                buttonEnable = false
                scope.launch {
                    delay(2000L)
                    buttonEnable = true
                }
            }
        ) {
            val text = if (buttonEnable) "Button Enabled" else "Button Disabled"
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

        Button(onClick = onButtonClick) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Text On Button\n\nTransition to Material3"
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
                    val text = "primary=${MaterialTheme.colors.primary.toArgb().toHexString()}\n" +
                            "primaryVariant=${MaterialTheme.colors.primaryVariant.toArgb().toHexString()}\n" +
                            "secondary=${MaterialTheme.colors.secondary.toArgb().toHexString()}\n" +
                            "secondaryVariant=${MaterialTheme.colors.secondaryVariant.toArgb().toHexString()}\n" +
                            "background=${MaterialTheme.colors.background.toArgb().toHexString()}\n" +
                            "surface=${MaterialTheme.colors.surface.toArgb().toHexString()}\n" +
                            "onPrimary=${MaterialTheme.colors.onPrimary.toArgb().toHexString()}\n" +
                            "onSecondary=${MaterialTheme.colors.onSecondary.toArgb().toHexString()}\n" +
                            "onBackground=${MaterialTheme.colors.onBackground.toArgb().toHexString()}\n" +
                            "onSurface=${MaterialTheme.colors.onSurface.toArgb().toHexString()}\n" +
                            "isLight=${MaterialTheme.colors.isLight}"
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
//fun Material2ContentsLightPreview() {
//    Material2Screen(darkTheme = false, navigateTo = {})
//}
//@Preview
//@Composable
//fun Material2ContentsDarkPreview() {
//    Material2Screen(darkTheme = true, navigateTo = {})
//}