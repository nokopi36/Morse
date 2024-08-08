package com.nokopi.morse

import androidx.compose.runtime.Composable
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@Composable
fun AccessibleForm() {
    var email by remember { mutableStateOf("") }
    var submissionStatus by remember { mutableStateOf("") }
    var charVibration by remember { mutableStateOf("") }
    val context = LocalContext.current
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)

    val brailleMap = mapOf(
        'a' to longArrayOf(0, 50), // Example Braille pattern for 'a'
        'b' to longArrayOf(0, 50, 100, 50),
        'c' to longArrayOf(0, 100),
        '.' to longArrayOf(0, 100, 100, 100),
        '@' to longArrayOf(0, 200),
        'o' to longArrayOf(0, 200, 200, 200),
        'm' to longArrayOf(0, 200, 200, 200, 200, 200),
        // Add mappings for other characters
    )

    val vibrate = { pattern: LongArray ->
        if (vibrator?.hasVibrator() == true) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        }
    }

    val validateEmail = { input: String ->
        when {
            input.isEmpty() -> {
                vibrate(longArrayOf(0, 100, 100, 100)) // Warning vibration
                "Email cannot be empty"
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches() -> {
                vibrate(longArrayOf(0, 100, 100, 100, 100, 100, 100, 100)) // Error vibration
                "Invalid email address"
            }
            else -> {
                vibrate(longArrayOf(0, 50)) // Success vibration
                null
            }
        }
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 100.dp)) {
        Text("Login Form", style = TextStyle(fontSize = 24.sp, color = Color.Black))

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = email,
            onValueChange = { newText ->
                email = newText
                newText.lastOrNull()?.let { char ->
                    brailleMap[char]?.let { pattern ->
                        charVibration = "Vibrating for $char ➡ ${pattern.asList()}"
                        vibrate(pattern)
                    }
                }
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    if (email.isEmpty()) {
                        Text("Enter your email", style = TextStyle(color = Color.Gray, fontSize = 18.sp))
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        if(charVibration.isNotEmpty()) {
            Text(charVibration, style = TextStyle(fontSize = 16.sp, color = Color.DarkGray))
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val emailError = validateEmail(email)

                submissionStatus = if (emailError == null) {
                    "Submission successful"
                } else {
                    "Submission failed: $emailError"
                }

                if (emailError == null) {
                    vibrate(longArrayOf(0, 50, 50, 50, 50, 50, 50, 50)) // Success vibration
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit", style = TextStyle(fontSize = 18.sp, color = Color.White))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if(submissionStatus.isNotEmpty()) {
            val textColor = if (submissionStatus.contains("failed")) Color.Red else Color.Green
            Text("Submission status ➡ $submissionStatus", style = TextStyle(fontSize = 16.sp, color = textColor))
        }
    }
}