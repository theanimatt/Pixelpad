package at.ac.fhstp.pixelpad.ui.theme

import androidx.compose.ui.graphics.Color

fun colorFromHex(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}

val Background = colorFromHex("#292F36")

val Gray = colorFromHex("#D9D9D9")
val Red = colorFromHex("#FFB1B1")
val Blue = colorFromHex("#9EDAFF")
val Green = colorFromHex("#C8FFD0")
val Yellow = colorFromHex("#FAFDB3")
