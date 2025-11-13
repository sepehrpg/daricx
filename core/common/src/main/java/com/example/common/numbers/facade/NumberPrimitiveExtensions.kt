package com.example.common.numbers.facade


import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.precision.PricePrecisionPolicy
import com.example.common.numbers.precision.RoundingBehavior
import com.example.common.numbers.scaling.CompactScaleStrategy
import com.example.common.numbers.scaling.ScaledNumber
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * Low-level extension utilities for working directly with:
 *  - Precision policies
 *  - Rounding behaviors
 *  - Compact scaling
 *  - Currency decoration
 *
 * These are useful for testing, debugging, or custom pipelines
 * where [NumberFormatting] façade is too high-level.
 */

// ---------------------------------------------------------------------------------------------
// PRECISION + ROUNDING
// ---------------------------------------------------------------------------------------------

/**
 * Rounds this value according to the provided [PricePrecisionPolicy] and [RoundingBehavior].
 *
 * Example:
 * ```
 * val policy = FixedPricePrecisionPolicy(2)
 * val result = 12.345.roundWith(policy, RoundingBehavior.RoundedUp)
 * println(result) // -> 12.35
 * ```
 */
fun Double.roundWith(
    policy: PricePrecisionPolicy,
    rounding: RoundingBehavior = RoundingBehavior.HalfEven
): Double {
    val digits = policy.fractionDigits(this)
    val mode = when (rounding) {
        RoundingBehavior.HalfEven    -> RoundingMode.HALF_EVEN
        RoundingBehavior.RoundedUp   -> RoundingMode.UP
        RoundingBehavior.RoundedDown -> RoundingMode.DOWN
    }
    return BigDecimal.valueOf(this).setScale(digits, mode).toDouble()
}


fun Double.roundToString(
    digits: Int,
): String {
    return "%.${digits}f".format(this)
}



/**
 * Fully formats this number using a precision [policy] and [rounding] strategy.
 * Handles locale separators, grouping, and zero trimming.
 *
 * Example:
 * ```
 * val policy = FixedPricePrecisionPolicy(3)
 * println(0.12345.formatWith(policy)) // -> "0.123"
 * ```
 */
fun Double.formatWith(
    policy: PricePrecisionPolicy,
    rounding: RoundingBehavior = RoundingBehavior.HalfEven,
    locale: Locale = Locale.US,
    trimZeros: Boolean = false,
    useGrouping: Boolean = true
): String {
    val digits = policy.fractionDigits(this)
    val mode = when (rounding) {
        RoundingBehavior.HalfEven    -> RoundingMode.HALF_EVEN
        RoundingBehavior.RoundedUp   -> RoundingMode.UP
        RoundingBehavior.RoundedDown -> RoundingMode.DOWN
    }
    val rounded = BigDecimal.valueOf(this).setScale(digits, mode).toDouble()
    val nf = NumberFormat.getNumberInstance(locale) as DecimalFormat
    nf.isGroupingUsed = useGrouping
    nf.minimumFractionDigits = if (trimZeros) 0 else digits
    nf.maximumFractionDigits = digits
    return nf.format(rounded)
}

// ---------------------------------------------------------------------------------------------
// COMPACT SCALING
// ---------------------------------------------------------------------------------------------

/**
 * Scales this number using a [CompactScaleStrategy].
 * Example: 2_450_000.0 → (2.45, "M")
 */
fun Double.scaleWith(strategy: CompactScaleStrategy): ScaledNumber = strategy.scale(this)

/**
 * Renders a scaled number to text, including suffix and optional currency decoration.
 *
 * @param fractionDigits number of decimals to keep
 * @param suffixSeparator string between number and suffix (e.g. "1.2 M" vs "1.2M")
 * @param useGrouping whether to apply digit grouping
 * @param currency optional currency decoration
 */
fun ScaledNumber.render(
    fractionDigits: Int = 2,
    locale: Locale = Locale.US,
    suffixSeparator: String = "",
    useGrouping: Boolean = false,
    currency: CurrencyStyle? = null
): String {
    val nf = NumberFormat.getNumberInstance(locale) as DecimalFormat
    nf.isGroupingUsed = useGrouping
    nf.minimumFractionDigits = 0
    nf.maximumFractionDigits = fractionDigits

    val core = nf.format(value)
    val withSuffix = if (suffix != null) core + suffixSeparator + suffix else core
    return currency?.decorate(withSuffix) ?: withSuffix
}

// ---------------------------------------------------------------------------------------------
// CURRENCY DECORATION ONLY
// ---------------------------------------------------------------------------------------------

/**
 * Decorates this pre-formatted numeric string with a currency symbol.
 *
 * Example:
 * ```
 * println("1,200.50".withCurrency(CurrencyStyle.usd())) // "$1,200.50"
 * println("12,000".withCurrency(CurrencyStyle.rial()))  // "12,000 ﷼"
 * ```
 */
fun String.withCurrency(currency: CurrencyStyle): String = currency.decorate(this)
