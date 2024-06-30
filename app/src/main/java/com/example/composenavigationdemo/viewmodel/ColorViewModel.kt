package com.example.composenavigationdemo.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull

class ColorViewModel : ViewModel() {
    private val _color: MutableStateFlow<Color?> = MutableStateFlow(null)
    val color: Flow<Color> = _color.filterNotNull()

    private val _darkMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val darkMode: Flow<Boolean> = _darkMode.asStateFlow()

    fun setColor(color: Color) {
        _color.value = color
    }

    fun toggleMode(dark: Boolean) {
        _darkMode.value = dark
    }
}