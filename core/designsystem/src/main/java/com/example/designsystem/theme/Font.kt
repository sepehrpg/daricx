package com.example.designsystem.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.designsystem.R


val AppFontEnglish = FontFamily(
    Font(R.font.urbanist_variable_font_wght, FontWeight.Normal),
    Font(R.font.urbanist_variable_font_wght, FontWeight.Medium),
    Font(R.font.urbanist_bold, FontWeight.Bold)
)


val AppFontPersian= FontFamily(
    Font(R.font.iransans, FontWeight.Normal),
    Font(R.font.iransans_medium, FontWeight.Medium),
    Font(R.font.iransans_black, FontWeight.Black),
    Font(R.font.iransans_bold, FontWeight.Bold),
    Font(R.font.iransans_light, FontWeight.Light),
    Font(R.font.iransans_ultralight, FontWeight.ExtraLight),

    Font(R.font.iransansnumber, FontWeight.Thin),
)
