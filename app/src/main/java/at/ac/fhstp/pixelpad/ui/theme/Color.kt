package at.ac.fhstp.pixelpad.ui.theme

import androidx.compose.ui.graphics.Color

fun colorFromHex(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}

val Background = colorFromHex("#3D2C6E")

//val Gray = colorFromHex("#747A8A")
val Priority1 = 0xFFFD6785.toInt()
val Priority2 = 0xFFFF956F.toInt()
val Priority3 = 0xFFFFE17D.toInt()
val Priority4 = 0xFF50FFD0.toInt()

