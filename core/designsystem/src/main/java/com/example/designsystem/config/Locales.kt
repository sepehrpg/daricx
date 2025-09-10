package com.example.designsystem.config

import java.util.Locale


fun isPersian(): Boolean {
    val lang = Locale.getDefault().language
    return lang == "fa" || lang == "ar"
}