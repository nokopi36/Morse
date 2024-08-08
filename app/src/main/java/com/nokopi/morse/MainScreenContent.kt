package com.nokopi.morse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nokopi.morse.feature.morse.MorseScreenContent

@Composable
fun MainScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        VibrationEx()
//        AccessibleForm()
        MorseScreenContent()
    }
}

@Preview
@Composable
private fun MainScreenContentPreview() {
    MainScreenContent()
}