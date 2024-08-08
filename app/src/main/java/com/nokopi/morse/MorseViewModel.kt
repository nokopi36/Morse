package com.nokopi.morse

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject


private const val DotDuration = 100L // 点の振動時間（ミリ秒）
private const val DashDuration = 200L // 線の振動時間（ミリ秒）
private const val GapDuration = 200L // 点と線の間の停止時間（ミリ秒）
private const val LetterGapDuration = 300L // 文字間の停止時間（ミリ秒）
private const val WordGapDuration = 1400L // 単語間の停止時間（ミリ秒）

// モールス信号のアルファベット対応表
private val MorseCodeMap = mapOf(
    'A' to "・-", 'B' to "-・・・", 'C' to "-・-・", 'D' to "-・・", 'E' to "・",
    'F' to "・・-・", 'G' to "--・", 'H' to "・・・・", 'I' to "・・", 'J' to "・---",
    'K' to "-・-", 'L' to "・-・・", 'M' to "--", 'N' to "-・", 'O' to "---",
    'P' to "・--・", 'Q' to "--・-", 'R' to "・-・", 'S' to "・・・", 'T' to "-",
    'U' to "・・-", 'V' to "・・・-", 'W' to "・--", 'X' to "-・・-", 'Y' to "-・--",
    'Z' to "--・・"
)

@HiltViewModel
class MorseViewModel @Inject constructor(

) : ViewModel() {

    fun vibrateMorseCode(
        message: String,
        context: Context,
    ) {
        val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
        val pattern = mutableListOf<Long>()
        pattern.add(0)

        message.uppercase(Locale.getDefault()).forEach { char ->
            if (char == ' ') {
                pattern.add(WordGapDuration)
            } else {
                val morseCode = MorseCodeMap[char]
                println("❤:$morseCode")
                morseCode?.forEachIndexed { index, symbol ->
                    when (symbol) {
                        '・' -> {
                            pattern.add(DotDuration)
                            if (index != morseCode.length - 1) {
                                pattern.add(GapDuration)
                            }
                        }

                        '-' -> {
                            pattern.add(DashDuration)
                            if (index != morseCode.length - 1) {
                                pattern.add(GapDuration)
                            }
                        }

                        ' ' -> {
                            pattern.add(LetterGapDuration)
                        }
                    }
                }
                pattern.add(LetterGapDuration)
            }
            println("$pattern")
        }

        val patternArray = pattern.toLongArray()
        println("Array:${patternArray}")

        vibrator?.vibrate(VibrationEffect.createWaveform(patternArray, -1))
    }
}