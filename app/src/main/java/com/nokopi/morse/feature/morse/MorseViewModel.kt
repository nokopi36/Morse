package com.nokopi.morse.feature.morse

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

private const val DotDuration = 80L // 点の振動時間（ミリ秒）
private const val DashDuration = DotDuration * 3 // 線の振動時間（ミリ秒）点の3倍の長さ
private const val GapDuration = DotDuration // 点と線の間の停止時間（ミリ秒）点と同じ長さ
private const val LetterGapDuration = DotDuration * 3 // 文字間の停止時間（ミリ秒）点の3倍の長さ
private const val WordGapDuration = DotDuration * 7 // 単語間の停止時間（ミリ秒）点の7倍の長さ

// モールス信号のアルファベット対応表
private val MorseCodeMap = mapOf(
    'A' to "・-", 'B' to "-・・・", 'C' to "-・-・", 'D' to "-・・", 'E' to "・",
    'F' to "・・-・", 'G' to "--・", 'H' to "・・・・", 'I' to "・・", 'J' to "・---",
    'K' to "-・-", 'L' to "・-・・", 'M' to "--", 'N' to "-・", 'O' to "---",
    'P' to "・--・", 'Q' to "--・-", 'R' to "・-・", 'S' to "・・・", 'T' to "-",
    'U' to "・・-", 'V' to "・・・-", 'W' to "・--", 'X' to "-・・-", 'Y' to "-・--",
    'Z' to "--・・",
)

@HiltViewModel
class MorseViewModel @Inject constructor() : ViewModel() {

    fun vibrateMorseCode(
        message: String,
        context: Context,
    ) {
        val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
        val pattern = mutableListOf<Long>()
        pattern.add(0)

        message.uppercase(Locale.getDefault()).forEachIndexed { index, char ->
            println("❤️：$char")
            if (char == ' ') {
                // 空白の場合、WordGapDuration を追加し、次の LetterGapDuration を追加しない
                pattern.add(WordGapDuration)
            } else {
                val morseCode = MorseCodeMap[char]
                println("❤:$morseCode")
                morseCode?.forEachIndexed { i, symbol ->
                    println("❤️symbol：$symbol")
                    when (symbol) {
                        '・' -> {
                            pattern.add(DotDuration)
                            if (i != morseCode.length - 1) {
                                pattern.add(GapDuration)
                            }
                        }

                        '-' -> {
                            pattern.add(DashDuration)
                            if (i != morseCode.length - 1) {
                                pattern.add(GapDuration)
                            }
                        }
                    }
                }

                // 最後の文字でなければ LetterGapDuration を追加
                if (index != message.length - 1 && message[index + 1] != ' ') {
                    pattern.add(LetterGapDuration)
                }
            }
            println("$pattern")
        }

        val patternArray = pattern.toLongArray()
        println("Array:${patternArray}")

        vibrator?.vibrate(VibrationEffect.createWaveform(patternArray, -1))
    }
}