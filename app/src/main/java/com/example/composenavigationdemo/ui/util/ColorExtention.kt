package com.example.composenavigationdemo.ui.util

import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.round

fun Color.shouldUseLight(): Boolean {
    return toHsl().run {
        l < 0.35f || (l < 0.5f && s < 0.5f) || (l < 0.55f && s < 0.4f)
                || (l < 0.7f) && h?.run {
            (16f / (1f + l + l * abs(h - 250f)) > 1f)
                    || (16f / (1f + l + l * abs(h - round(h / 360f) * 360f)) > 1f)
        } ?: false
    }
}

fun Color.brighten(target: Float = 0.5f): Color {
    var hsl = toHsl().brighten()
    while (hsl.l < target) {
        hsl = hsl.brighten()
    }
    return hsl.toColor()
}

fun Color.darken(target: Float = 0.5f): Color {
    var hsl = toHsl().darken()
    while (hsl.l > target) {
        hsl = hsl.darken()
    }
    return hsl.toColor()
}

private fun Color.toHsl(): HslColor {
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

fun Float.coerce(min: Float, max: Float): Float {
    return this.coerceAtLeast(min).coerceAtMost(max)
}