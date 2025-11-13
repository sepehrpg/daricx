package com.example.common.numbers.facade

import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.grouping.DigitGroupingFormatter
import com.example.common.numbers.precision.PricePrecisionPolicy
import com.example.common.numbers.precision.RoundingBehavior
import com.example.common.numbers.scaling.CompactScaleStrategy
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * A façade for number rendering in UI layers.
 *
 * Responsibilities:
 * - Decide fraction digits via a [PricePrecisionPolicy].
 * - Round using a [RoundingBehavior].
 * - Optionally scale large numbers via a [CompactScaleStrategy] (e.g., K/M/B/T).
 * - Apply 3-digit grouping via a [DigitGroupingFormatter].
 * - Decorate the final string with a [CurrencyStyle] (prefix/suffix with/without space).
 *
 * Keep the public API simple while delegating details to injected collaborators.
 */
class NumberFormatting private constructor(
    private val scaler: CompactScaleStrategy,
    private val policy: PricePrecisionPolicy,
    private val rounding: RoundingBehavior,
    private val grouping: DigitGroupingFormatter,
    /**
     * String inserted between the formatted number and the compact suffix (e.g., "1.5 M" vs "1.5M").
     * Keep empty by default for finance displays.
     */
    private val suffixSeparator: String = ""
) {

    /** Maps [RoundingBehavior] to JVM [RoundingMode]. */
    private fun roundingMode(): RoundingMode = when (rounding) {
        RoundingBehavior.HalfEven    -> RoundingMode.HALF_EVEN
        RoundingBehavior.RoundedUp   -> RoundingMode.UP
        RoundingBehavior.RoundedDown -> RoundingMode.DOWN
    }

    /**
     * Formats a number with fixed/trimmed fraction digits and grouping configured by [grouping].
     * If [trimZeros] is true, trailing zeros are trimmed up to [digits];
     * otherwise exactly [digits] decimals are shown.
     */
    private fun formatFixed(
        value: Double,
        digits: Int,
        trimZeros: Boolean,
        locale: Locale,
        // Optional override for grouping in this call (e.g., disable when suffixed)
        overrideGrouping: Boolean? = null
    ): String {
        val nf = NumberFormat.getNumberInstance(locale) as DecimalFormat

        // Apply grouping policy (or override)
        if (overrideGrouping == null) {
            grouping.applyTo(nf, locale)
        } else {
            nf.isGroupingUsed = overrideGrouping
            if (overrideGrouping) grouping.applyTo(nf, locale) // ensures size/separator if enabled
        }

        if (trimZeros) {
            nf.minimumFractionDigits = 0
            nf.maximumFractionDigits = digits
        } else {
            nf.minimumFractionDigits = digits
            nf.maximumFractionDigits = digits
        }
        return nf.format(value)
    }

    // ---------------------------------------------------------------------------------------------
    // Public API
    // ---------------------------------------------------------------------------------------------

    /**
     * Pretty price formatting (policy-driven fraction digits + rounding + grouping + currency).
     */
    fun pricePretty(
        price: Double,
        currency: CurrencyStyle,
        trimZeros: Boolean = false,
        locale: Locale = Locale.US
    ): String {
        val digits = policy.fractionDigits(price)
        val rounded = BigDecimal.valueOf(price).setScale(digits, roundingMode()).toDouble()
        val core = formatFixed(rounded, digits, trimZeros, locale)
        return currency.decorate(core)
    }

    /**
     * Compact number formatting (e.g., 4.72B / 15.3M / 532K) + optional currency decoration.
     *
     * Behavior:
     *  - If [n] is null, returns "—".
     *  - If suffix exists (K/M/B/...), formats scaled value with fixed decimals.
     *    By default grouping is disabled for the scaled branch (to satisfy "$1000.000M" style).
     *  - If no suffix (below first threshold), formats as integer using [grouping].
     *
     * @param useGroupingWhenSuffixed controls grouping in suffix branch (default false).
     */
    fun compactNumber(
        n: Double?,
        locale: Locale = Locale.US,
        fractionDigits: Int = 2,
        currency: CurrencyStyle? = null,
        useGroupingWhenSuffixed: Boolean = false
    ): String {
        if (n == null) return "—"

        val (scaledValue, suffix) = scaler.scale(n)

        val withSuffix = if (suffix != null) {
            // Fixed decimals; grouping optionally disabled/enabled per flag
            val core = formatFixed(
                value = scaledValue,
                digits = fractionDigits,
                trimZeros = false,
                locale = locale,
                overrideGrouping = useGroupingWhenSuffixed
            )
            core + suffixSeparator + suffix
        } else {
            // Below first threshold → integer formatted by grouping policy
            grouping.formatInteger(scaledValue.toLong(), locale)
        }

        return currency?.decorate(withSuffix) ?: withSuffix
    }

    companion object {
        /**
         * Factory for constructing a [NumberFormatting] façade.
         */
        fun defaults(
            scaler: CompactScaleStrategy,
            policy: PricePrecisionPolicy,
            rounding: RoundingBehavior = RoundingBehavior.HalfEven,
            grouping: DigitGroupingFormatter = DigitGroupingFormatter.locale(),
            suffixSeparator: String = ""
        ): NumberFormatting = NumberFormatting(
            scaler = scaler,
            policy = policy,
            rounding = rounding,
            grouping = grouping,
            suffixSeparator = suffixSeparator
        )
    }
}
