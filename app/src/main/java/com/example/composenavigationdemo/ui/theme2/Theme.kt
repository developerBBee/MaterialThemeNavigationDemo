package com.example.composenavigationdemo.ui.theme2

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.round

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
    val veryBright = primaryColor.brighten().brighten()
    val dark = primaryColor.darken()
    val veryDark = primaryColor.darken().darken()

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

fun Color.shouldUseLight(): Boolean {
    return toHsl().run {
        l < 0.35f || (l < 0.5f && s < 0.5f) || (l < 0.55f && s < 0.4f)
                || (l < 0.7f) && h?.run {
                    (16f / (1f + l + l * abs(h - 250f)) > 1f)
                            || (16f / (1f + l + l * abs(h - round(h / 360f) * 360f)) > 1f)
                } ?: false
    }
}

fun Color.brighten(): Color {
    return toHsl().brighten().toColor()
}

fun Color.darken(): Color {
    return toHsl().darken().toColor()
}

fun Color.toHsl(): HslColor {
    val max = maxOf(red, green, blue)
    val min = minOf(red, green, blue)

    val l = if (max == 1f && min == 1f) 1f else (max + min) / 2f

    val d = max - min
    val (h, s) = when (min) {
        max -> null
        blue -> 60f * (green - red) / d + 60f
        red -> 60f * (blue - green) / d + 180f
        green -> (60f * (red - blue) / d + 300f)
        else -> null
    } to if (max == min || l == 1f || l == 0f) 0f else if (min == 0f) 1f else d / (1f - abs(2f * l - 1f))

    return HslColor(h?.coerce(0f, 360f), s.coerce(0f, 1f), l.coerce(0f, 1f))
}

/**
 * HSL色空間 円柱モデル
 * H 色相
 * S 彩度
 * L 輝度
 */
data class HslColor(val h: Float?, val s: Float, val l: Float) {
    init {
        require(s in 0f..1f)
        require(l in 0f..1f)
    }

    fun brighten(): HslColor {
        if (l >= 0.8f) return this // 80%以上はそれ以上明るくしない

        return copy(l = (l + 0.1f).coerceAtMost(0.8f))
    }

    fun darken(): HslColor {
        if (l <= 0.2f) return this // 20%以下はそれ以上暗くしない

        return copy(l = (l - 0.1f).coerceAtLeast(0.2f))
    }

    fun toColor(): Color {
        val max = l + s * (1f - abs(2f * l - 1f)) / 2f
        val min = l - s * (1f - abs(2f * l - 1f)) / 2f

        val (r, g, b) = when {
            h == null -> Triple(l, l, l)
            (0f <= h && h < 60f) -> Triple(max, min + (max - min) * h / 60f, min)
            (60f <= h && h < 120f) -> Triple(min + (max - min) * (120f - h) / 60f, max, min)
            (120f <= h && h < 180f) -> Triple(min, max, min + (max - min) * (h - 120f) / 60f)
            (180f <= h && h < 240f) -> Triple(min, min + (max - min) * (240f - h) / 60f, max)
            (240f <= h && h < 300f) -> Triple(min + (max - min) * (h - 240f) / 60f, min, max)
            (300f <= h && h < 360f) -> Triple(max, min, min + (max - min) * (360f - h) / 60f)
            else -> Triple(l, l, l)
        }

        return Color(r.coerce(0f, 1f), g.coerce(0f, 1f), b.coerce(0f, 1f))
    }
}

fun Float.coerce(min: Float, max: Float): Float {
    return this.coerceAtLeast(min).coerceAtMost(max)
}