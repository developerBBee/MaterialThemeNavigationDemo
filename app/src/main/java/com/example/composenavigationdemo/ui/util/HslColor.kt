package com.example.composenavigationdemo.ui.util

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

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
