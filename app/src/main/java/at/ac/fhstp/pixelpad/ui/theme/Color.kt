package at.ac.fhstp.pixelpad.ui.theme

import androidx.compose.ui.graphics.Color

fun colorFromHex(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}

val Background = colorFromHex("#3D2C6E")

//val Gray = colorFromHex("#747A8A")
val Priority1 = colorFromHex("#FD6785")
//val Blue = colorFromHex("#399CAD")
val Priority4 = colorFromHex("#50FFD0")
val Priority3 = colorFromHex("#FFE17D")
val Priority2 = colorFromHex("#FF956F")
