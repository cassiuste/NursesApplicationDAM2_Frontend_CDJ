package com.example.frontendnursesapplication.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val PurpleStucom = Color(0xFF8449BF)

val BlueStucom = Color(0xFF2E6FF2)

val RedStucom = Color(0xFFF23030)

val PinkStucom = Color(0xFFF27E7E)

val BlackStucom = Color(0xFF0D0D0D)

val WhiteStucom = Color(0xFFF2F2F2)

val LightBlueWhite = Color(0xFFF1F5F9)

val BlueGray = Color(0xFF334155)

val SoftRed = Color(0xFFD04A4A)

val ColorScheme.focusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else Color.Black

val ColorScheme.unfocusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF94A388) else Color(0xFF475569)

val ColorScheme.textFieldContainer
    @Composable
    get() = if (isSystemInDarkTheme()) BlueGray.copy(alpha = 0.6f ) else LightBlueWhite