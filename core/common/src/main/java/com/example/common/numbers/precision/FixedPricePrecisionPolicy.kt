package com.example.common.numbers.precision

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * FixedPricePrecisionPolicy:
 *
 * A simple precision policy that always enforces an exact number of fraction digits.
 *
 * Example:
 * ```
 * val policy = FixedPricePrecisionPolicy(digits = 2)
 * policy.applyRounding(12.3456)  // -> 12.35
 * policy.applyRounding(0.1)      // -> 0.10
 * ```
 *
 * It can be combined with:
 *  - [NegativePrecisionMode] to define behavior for negative values.
 *  - [RoundingBehavior] to control rounding direction.
 *
 * This class is intentionally minimal and extendable (SRP & OCP).
 */
class FixedPricePrecisionPolicy(
    private val digits: Int,
    private val negativeMode: NegativePrecisionMode = NegativePrecisionMode.MirrorPositive,
    private val rounding: RoundingBehavior = RoundingBehavior.HalfEven
) : PricePrecisionPolicy {

    init {
        require(digits >= 0) { "digits must be >= 0" }
    }

    /**
     * Always returns the configured number of fraction digits.
     * For negative values, applies [NegativePrecisionMode].
     */
    override fun fractionDigits(value: Double): Int {
        return if (value < 0 && negativeMode is NegativePrecisionMode.Fixed) {
            negativeMode.digits
        } else {
            digits
        }
    }

    /**
     * Rounds the given [value] according to the configured
     * number of digits and [RoundingBehavior].
     */
    fun applyRounding(value: Double): Double {
        val d = fractionDigits(value)
        val mode = when (rounding) {
            RoundingBehavior.HalfEven    -> RoundingMode.HALF_EVEN
            RoundingBehavior.RoundedUp   -> RoundingMode.UP      // away from zero
            RoundingBehavior.RoundedDown -> RoundingMode.DOWN    // toward zero
        }


        return BigDecimal.valueOf(value).setScale(d, mode).toDouble()
    }



    /**
     * Same as [applyRounding] but returns a String keeping trailing zeros.
     * Example:
     *  - value=1.23, digits=4 → "1.2300"
     *  - value=0.0045, digits=6 → "0.004500"
     */
    fun applyRoundingString(value: Double): String {
        val digits = fractionDigits(value)
        val mode = when (rounding) {
            RoundingBehavior.HalfEven   -> RoundingMode.HALF_EVEN
            RoundingBehavior.RoundedUp  -> RoundingMode.UP
            RoundingBehavior.RoundedDown-> RoundingMode.DOWN
        }

        val bd = BigDecimal(value).setScale(digits, mode)
        // String.format guarantees fixed decimal places including zeros
        return "%.${digits}f".format(bd.toDouble())
    }

    companion object {
        val oneDigit = FixedPricePrecisionPolicy(1)
    }
}