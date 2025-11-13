package com.example.common.numbers.scaling



/**
 * Represents the result of scaling a number for compact display: value + suffix.
 * Example: 1_500 -> (1.5, "K")
 */
data class ScaledNumber(val value: Double, val suffix: String?)