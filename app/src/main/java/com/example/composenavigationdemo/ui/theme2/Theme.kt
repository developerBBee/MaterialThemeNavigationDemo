package com.example.composenavigationdemo.ui.theme2

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.composenavigationdemo.ui.util.brighten
import com.example.composenavigationdemo.ui.util.darken
import com.example.composenavigationdemo.ui.util.shouldUseLight

val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
)

val LightColorPalette = lightColors(
    primary = Orange500,
    primaryVariant = Orange700,
    secondary = Orange200,
    secondaryVariant = Orange200,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun Material2Theme(
    colorPalette: Colors = LightColorPalette,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

//// LIGHT
//val primaryL: Color = Color(0xFF6200EE)
//val primaryVariantL: Color = Color(0xFF3700B3)
//val secondaryL: Color = Color(0xFF03DAC6)
//val secondaryVariantL: Color = Color(0xFF018786)
//
//// DARK
//val primary: Color = Color(0xFFBB86FC)
//val primaryVariant: Color = Color(0xFF3700B3)
//val secondary: Color = Color(0xFF03DAC6)
//val secondaryVariant: Color = secondary


fun generate(
    primaryColor: Color,
    useLight: Boolean = primaryColor.shouldUseLight()
): Colors {
    val bright = primaryColor.brighten()
    val veryBright = primaryColor.brighten(target = 0.7f)
    val dark = primaryColor.darken()
    val veryDark = primaryColor.darken(target = 0.3f)

    return if (useLight) {
        lightColors(
            primary = primaryColor,
            primaryVariant = dark,
            secondary = veryBright,
            secondaryVariant = bright,
        )
    } else {
        darkColors(
            primary = primaryColor,
            primaryVariant = veryDark,
            secondary = bright,
            secondaryVariant = bright,
        )
    }
}