package com.nokopi.morse.feature.morse

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.nokopi.morse.MorseViewModel
import com.nokopi.morse.feature.morse.component.MorseTextInput

@Composable
internal fun MorseScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MorseViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ) {
        MorseTextInput(
            onButtonClick = { message ->
                viewModel.vibrateMorseCode(message, context)
            }
        )
    }
}