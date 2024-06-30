package com.example.composenavigationdemo.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.composenavigationdemo.ui.theme.DarkColorScheme
import com.example.composenavigationdemo.ui.theme.LightColorScheme
import com.example.composenavigationdemo.ui.theme.Material3Theme

@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    negativeText: String? = null,
    positiveText: String,
    onNegativeClick: () -> Unit = {},
    onPositiveClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = {}
    ) {
        Surface(
            modifier = modifier.fillMaxSize(0.9f),
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor,
            border = border,
            tonalElevation = elevation,
            shadowElevation = elevation
        ) {
            Column(verticalArrangement = Arrangement.Bottom) {
                Box(modifier = Modifier.weight(1f)) {
                    content()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    negativeText?.let {
                        Button(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f),
                            onClick = onNegativeClick
                        ) {
                            Text(text = it)
                        }
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        onClick = onPositiveClick
                    ) {
                        Text(text = positiveText)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BaseDialogLightPreview() {
    Material3Theme(colorScheme = LightColorScheme) {
        BaseDialog(
            backgroundColor = MaterialTheme.colorScheme.surface,
            negativeText = "Cancel",
            positiveText = "OK",
            onNegativeClick = {},
            onPositiveClick = {}
        ) {
            // Content
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Hello")
            }
        }
    }
}

@Preview
@Composable
fun BaseDialogDarkPreview() {
    Material3Theme(colorScheme = DarkColorScheme) {
        BaseDialog(positiveText = "OK", onPositiveClick = {}) {
            // Content
        }
    }
}