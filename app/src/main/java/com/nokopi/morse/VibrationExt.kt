package com.nokopi.morse

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// バイブレーション機能の例
@Composable
fun VibrationEx(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        5000L,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
        ) {
            Text(text = "Vibration")
        }

        OutlinedButton(
            onClick = {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 200, 100, 100, 100, 100),
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
        ) {
            Text(text = "morse")
        }

        var isActive by remember { mutableStateOf(false) }

        FilledIconToggleButton(
            checked = isActive,
            onCheckedChange = {
                isActive = !isActive
                if (isActive) {
                    vibrator.vibrate(
                        VibrationEffect.createWaveform(
                            longArrayOf(0, 500, 300),
                            1
                        )
                    )
                } else {
                    vibrator.cancel()
                }
            },
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null
            )
        }

        Button(
            onClick = {
                vibrator.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                )
            },
            modifier = Modifier
                .padding(top = 64.dp)
        ) {
            Text(text = "Click Effect")
        }
        Button(
            onClick = {
                vibrator.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
                )
            },
            modifier = Modifier
                .padding(vertical = 32.dp)
        ) {
            Text(text = "Heavy Click Effect")
        }
        Button(
            onClick = {
                vibrator.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
                )
            }
        ) {
            Text(text = "Double Click Effect")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VibrationExPreview() {
    VibrationEx()
}