package com.example.composenavigationdemo.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.composenavigationdemo.ui.util.brighten
import com.example.composenavigationdemo.ui.util.darken
import com.example.composenavigationdemo.ui.util.shouldUseLight

val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

fun generate(
    primaryColor: Color,
    useLight: Boolean = primaryColor.shouldUseLight()
): ColorScheme {
    val bright = primaryColor.brighten()
    val veryBright = primaryColor.brighten().brighten()
    val dark = primaryColor.darken()
    val veryDark = primaryColor.darken().darken()

    return if (useLight) {
        lightColorScheme(
            primary = primaryColor,
            secondary = dark,
            tertiary = bright,

            background = Color(0xFFFFFBFE),
            surface = Color(0xFFFFFBFE),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onTertiary = Color.White,
            onBackground = Color(0xFF1C1B1F),
            onSurface = Color(0xFF1C1B1F),
        )
    } else {
        darkColorScheme(
            primary = primaryColor,
            secondary = veryDark,
            tertiary = veryBright,

            background = Color(0xFF1C1B1F),
            surface = Color(0xFF1C1B1F),
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onTertiary = Color.Black,
            onBackground = Color(0xFFFFFBFE),
            onSurface = Color(0xFFFFFBFE),
        )
    }
}

val ColorScheme.shouldLight: Boolean
    get() = primary.shouldUseLight()

@Composable
fun Material3Theme(
    colorScheme: ColorScheme = generate(Orange500),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}