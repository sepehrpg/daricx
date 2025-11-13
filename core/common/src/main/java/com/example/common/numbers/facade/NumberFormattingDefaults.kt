package com.example.common.numbers.facade

import com.example.common.numbers.grouping.DigitGroupingFormatter
import com.example.common.numbers.precision.AutoPricePrecisionPolicy
import com.example.common.numbers.precision.FixedPricePrecisionPolicy
import com.example.common.numbers.precision.RoundingBehavior
import com.example.common.numbers.scaling.CompactScaleStrategy

/**
 * Centralized default instances of [NumberFormatting] for app-wide usage.
 *
 * - [default]: a sensible default for crypto/finance apps with Auto precision and HalfEven rounding.
 * - [fiat]:    a variant intended for fiat pricing; switch the policy to Fixed(2) if needed.
 *
 * Configure these once and inject them (DI) or provide via CompositionLocal in UI.
 */
object NumberFormattingDefaults {

    /** General default for crypto/finance displays (Auto precision, HalfEven, international scaling). */
    val default: NumberFormatting = NumberFormatting.defaults(
        scaler = CompactScaleStrategy.international(),
        policy = FixedPricePrecisionPolicy(0),
        rounding = RoundingBehavior.HalfEven,
        grouping = DigitGroupingFormatter.locale(),
        suffixSeparator = "" // set to " " to render like "1.53 M"
    )

    val defaultWithAutoPrecision: NumberFormatting = NumberFormatting.defaults(
        scaler = CompactScaleStrategy.international(),
        policy = AutoPricePrecisionPolicy(),
        rounding = RoundingBehavior.HalfEven,
        grouping = DigitGroupingFormatter.locale(),
        suffixSeparator = "" // set to " " to render like "1.53 M"
    )

    /**
     * Fiat-oriented default. You can swap AutoPricePrecisionPolicy() with FixedPricePrecisionPolicy(2)
     * if you want strictly 2 digits for all fiat amounts.
     */
    val fiat: NumberFormatting = NumberFormatting.defaults(
        scaler = CompactScaleStrategy.international(),
        policy = AutoPricePrecisionPolicy(),
        rounding = RoundingBehavior.HalfEven,
        grouping = DigitGroupingFormatter.locale()
    )
}
