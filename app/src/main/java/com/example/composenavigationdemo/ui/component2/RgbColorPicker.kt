package com.example.composenavigationdemo.ui.component2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RgbColorPicker(
    modifier: Modifier = Modifier,
    value: Color,
    onColorChanged: (Color) -> Unit,
) {
    var red by remember { mutableFloatStateOf(value.red) }
    var green by remember { mutableFloatStateOf(value.green) }
    var blue by remember { mutableFloatStateOf(value.blue) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .width(160.dp)
                .height(90.dp)
                .background(Color(red, green, blue))
                .clip(shape = RoundedCornerShape(16.dp))
        )
        ColorSlider(
            value = red,
            color = Color.Red,
            onValueChange = {
                red = it
                onColorChanged(Color(red, green, blue))
            }
        )
        ColorSlider(
            value = green,
            color = Color.Green,
            onValueChange = {
                green = it
                onColorChanged(Color(red, green, blue))
            }
        )
        ColorSlider(
            value = blue,
            color = Color.Blue,
            onValueChange = {
                blue = it
                onColorChanged(Color(red, green, blue))
            }
        )
    }
}

@Composable
fun ColorSlider(value: Float, color: Color, onValueChange: (Float) -> Unit) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "${colorToString(color)}: ${(value * 255).toInt()}",
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(activeTrackColor = color)
        )
    }
}

fun colorToString(color: Color): String {
    return when (color) {
        Color.Red -> "Red"
        Color.Green -> "Green"
        Color.Blue -> "Blue"
        else -> "Unknown"
    }
}