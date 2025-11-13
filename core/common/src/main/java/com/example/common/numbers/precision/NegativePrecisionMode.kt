package com.example.common.numbers.precision

/**
 * NegativePrecisionMode defines how to treat negative numbers' precision.
 */
sealed interface NegativePrecisionMode {
    /** Mirrors positive rules (recommended). */
    object MirrorPositive : NegativePrecisionMode

    /** Forces a fixed number of fraction digits for negatives. */
    data class Fixed(val digits: Int) : NegativePrecisionMode
}
