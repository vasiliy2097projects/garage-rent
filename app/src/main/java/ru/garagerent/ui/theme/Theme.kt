package ru.garagerent.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Ink = Color(0xFF1C1B19)
val Accent = Color(0xFF2F6F4E)
val Pin = Color(0xFF1E3A2F)
val Paper = Color(0xFFE6E2D6)
val Block = Color(0xFFF7F5F0)
val Sheet = Color(0xFFFFFFFF)
val Muted = Color(0xFF8E897F)
val ChipBorder = Color(0xFFD5D0C6)
val SearchIndicator = Color(0xFFDCEBE3)

private val Colors = lightColorScheme(
    primary = Accent,
    onPrimary = Color.White,
    background = Paper,
    surface = Sheet,
    onSurface = Ink,
)

@Composable
fun GarageRentTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Colors,
        content = content,
    )
}
