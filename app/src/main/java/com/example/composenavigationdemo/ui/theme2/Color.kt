package com.example.composenavigationdemo.ui.theme2

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Teal300 = Color(0xFF03BAA5)
val Orange200 = Color(0xFFFCBB86)
val Orange500 = Color(0xFFEE6200)
val Orange700 = Color(0xFFB33700)

fun getComplementaryColor(color: Color): Color {
    val red = color.red
    val green = 1.0f - color.green
    val blue = 1.0f - color.blue
    return Color(red, green, blue)
}

val Red500 = Color(0xFFf44336)
val Blue500 = Color(0xFFe91e63)

val BrandColors = setOf(
    Purple500,

)