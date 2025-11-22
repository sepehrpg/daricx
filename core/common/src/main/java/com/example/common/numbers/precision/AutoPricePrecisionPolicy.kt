package com.example.common.numbers.precision

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ln


/**
 * Default  price precision policy:
 *  - >= 1         -> 2 decimals
 *  - >= 0.1       -> 3 decimals
 *  - >= 0.01      -> 4 decimals
 *  - >= 0.001     -> 5 decimals
 *  - (0, 0.001)   -> clamp(6..10) using 2 - log10(p)
 *  - == 0         -> 0
 *  - < 0          -> same as positive abs(value) unless NegativePrecisionMode.Fixed(...)
 *
 * Additionally, a rounding behavior is injectable (default HalfEven).
 * The policy remains SRP for digits; applyRounding(...) is provided as an opt-in helper.
 */
class AutoPricePrecisionPolicy(
    private val negativeMode: NegativePrecisionMode = NegativePrecisionMode.MirrorPositive,
    private val tinyDigitsRange: IntRange = 6..10,
    private val rounding: RoundingBehavior = RoundingBehavior.HalfEven
) : PricePrecisionPolicy {
    override fun fractionDigits(value: Double): Int {
        if (value == 0.0) return 0
        if (value < 0.0) {
            return when (negativeMode) {
                is NegativePrecisionMode.MirrorPositive -> fractionDigits(-value)
                is NegativePrecisionMode.Fixed -> negativeMode.digits
            }
        }
        return when {
            value >= 1.0   -> 2
            value >= 0.1   -> 3
            value >= 0.01  -> 4
            value >= 0.001 -> 5
            value > 0.0    -> {
                // digits = clip[6..10](floor(2 - log10(value)))
                val base10 = ln(value) / ln(10.0) // or kotlin.math.log10(value)
                (2 - base10).toInt().coerceIn(tinyDigitsRange.first, tinyDigitsRange.last)
            }
            else           -> 6 // defensive; negatives handled above
        }
    }

    /**
     * Helper to round a value according to the decided digits and the configured rounding behavior.
     * This keeps the "digits decision" SRP intact while still allowing a ready-to-use round step.
     */
    fun applyRounding(value: Double): Double {
        val digits = fractionDigits(value)
        if (digits <= 0) return when (rounding) {
            RoundingBehavior.RoundedUp -> roundIntAwayFromZero(value)
            RoundingBehavior.RoundedDown -> roundIntTowardZero(value)
            RoundingBehavior.HalfEven -> BigDecimal(value).setScale(0, RoundingMode.HALF_EVEN).toDouble()
        }

        val bd = BigDecimal(value)
        val mode = when (rounding) {
            RoundingBehavior.HalfEven   -> RoundingMode.HALF_EVEN
            RoundingBehavior.RoundedUp  -> RoundingMode.UP      // away from zero
            RoundingBehavior.RoundedDown-> RoundingMode.DOWN    // toward zero
        }
        return bd.setScale(digits, mode).toDouble()
    }


    private fun roundIntAwayFromZero(v: Double): Double =
        BigDecimal(v).setScale(0, RoundingMode.UP).toDouble()

    private fun roundIntTowardZero(v: Double): Double =
        BigDecimal(v).setScale(0, RoundingMode.DOWN).toDouble()
}