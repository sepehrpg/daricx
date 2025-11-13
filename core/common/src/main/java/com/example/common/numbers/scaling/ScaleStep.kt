package com.example.common.numbers.scaling


/**
 * A single scale step with threshold, divisor, and suffix.
 */
data class ScaleStep(
    val threshold: Double,
    val divisor: Double,
    val suffix: String
)