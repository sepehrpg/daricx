package com.example.designsystem.extension


fun String?.nonBlankOrNull() = this?.takeIf { it.isNotBlank() }