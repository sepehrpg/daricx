package com.example.common.numbers.precision



/**
 * PricePrecisionPolicy encapsulates how many fraction digits to use for a given price.
 * This interface is intentionally minimal (SRP): it ONLY decides digits, not formatting.
 */
fun interface PricePrecisionPolicy {
    fun fractionDigits(value: Double): Int
}



