package com.nokopi.morse.feature.morse.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun MorseTextInput(
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (message, setMessage) = remember { mutableStateOf("sos") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = message,
            onValueChange = { newText ->
                setMessage(newText)
            },
            singleLine = true,
        )
        Button(onClick = { onButtonClick(message) }) {
            Text(text = "モールスへ変換")
        }
    }
}

@Preview
@Composable
private fun MorseTextInputPreview() {
    MorseTextInput(
        onButtonClick = {},
    )
}