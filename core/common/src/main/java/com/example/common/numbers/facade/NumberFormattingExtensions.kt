package com.example.common.numbers.facade

import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.facade.NumberFormatting
import com.example.common.numbers.facade.NumberFormattingDefaults
import java.util.Locale

/**
 * Extension utilities for applying [NumberFormatting] façade directly on primitive numeric types.
 *
 * These provide a very clean syntax for UI and presentation layers.
 * Example:
 * ```
 * val price = 1234.567
 * println(price.prettyUSD())     // "$1,234.57"
 * println(price.compactText())   // "1.23K"
 * println(2500000.0.prettyBTC()) // "₿2.50M"
 * ```
 */

// ---------------------------------------------------------------------------------------------
// PRICE FORMATTING (policy + rounding + grouping + currency)
// ---------------------------------------------------------------------------------------------

/**
 * Formats this [Double] as a price string using a [NumberFormatting] instance.
 *
 * @param currency  How to decorate the formatted number (e.g., "$", "BTC", "﷼").
 * @param trimZeros Whether to trim trailing zeros (e.g., 12.340 → 12.34).
 * @param locale    Locale controlling decimal/thousands separators.
 * @param formatter The [NumberFormatting] façade that handles precision, rounding, and grouping.
 */
fun Double.prettyPrice(
    currency: CurrencyStyle= CurrencyStyle.usd(),
    trimZeros: Boolean = false,
    locale: Locale = Locale.US,
    formatter: NumberFormatting = NumberFormattingDefaults.default
): String = formatter.pricePretty(
    price = this,
    currency = currency,
    trimZeros = trimZeros,
    locale = locale
)


fun Double.prettyPriceCrypto(
    currency: CurrencyStyle = CurrencyStyle.usd(),
    trimZeros: Boolean = false,
    locale: Locale = Locale.US,
    formatter: NumberFormatting = NumberFormattingDefaults.defaultWithAutoPrecision
): String = formatter.pricePretty(
    price = this,
    currency = currency,
    trimZeros = trimZeros,
    locale = locale
)



/** Shorthand for USD currency — e.g., `$12.34`. */
fun Double.prettyUSD(
    trimZeros: Boolean = false,
    locale: Locale = Locale.US,
    formatter: NumberFormatting = NumberFormattingDefaults.default
): String = prettyPrice(CurrencyStyle.usd(), trimZeros, locale, formatter)

/** Shorthand for Bitcoin currency — e.g., `₿0.0015`. */
fun Double.prettyBTC(
    trimZeros: Boolean = false,
    locale: Locale = Locale.US,
    formatter: NumberFormatting = NumberFormattingDefaults.defaultWithAutoPrecision
): String = prettyPrice(CurrencyStyle.btcPrefix(), trimZeros, locale, formatter)

/** Shorthand for Iranian Rial — e.g., `12,000 ﷼`. */
fun Double.prettyRial(
    trimZeros: Boolean = false,
    locale: Locale = Locale.US,
    formatter: NumberFormatting = NumberFormattingDefaults.default
): String = prettyPrice(CurrencyStyle.rial(), trimZeros, locale, formatter)

// ---------------------------------------------------------------------------------------------
// COMPACT NUMBER (scaling + fraction digits + optional currency)
// ---------------------------------------------------------------------------------------------

/**
 * Formats this number compactly (e.g., 53200 → "53.2K").
 *
 * @param fractionDigits          Number of decimals after scaling.
 * @param locale                  Locale controlling separators.
 * @param currency                Optional currency decoration.
 * @param useGroupingWhenSuffixed Whether grouping should apply when suffix (K/M/B/T) is used.
 * @param formatter               The façade performing scaling & formatting logic.
 */
fun Double.compactText(
    fractionDigits: Int = 2,
    locale: Locale = Locale.US,
    currency: CurrencyStyle? = null,
    useGroupingWhenSuffixed: Boolean = false,
    formatter: NumberFormatting = NumberFormattingDefaults.default
): String = formatter.compactNumber(
    n = this,
    locale = locale,
    fractionDigits = fractionDigits,
    currency = currency,
    useGroupingWhenSuffixed = useGroupingWhenSuffixed
)

/**
 * Null-safe variant for API values that might be missing.
 * Returns "—" when the receiver is null.
 */
fun Double?.compactTextOrDash(
    fractionDigits: Int = 2,
    locale: Locale = Locale.US,
    currency: CurrencyStyle? = null,
    useGroupingWhenSuffixed: Boolean = false,
    formatter: NumberFormatting = NumberFormattingDefaults.default
): String = if (this == null) "—" else this.compactText(
    fractionDigits = fractionDigits,
    locale = locale,
    currency = currency,
    useGroupingWhenSuffixed = useGroupingWhenSuffixed,
    formatter = formatter
)

// ---------------------------------------------------------------------------------------------
// INT / LONG convenience helpers
// ---------------------------------------------------------------------------------------------

/** Converts this [Int] to [Double] and applies [prettyUSD] formatting. */
fun Int.prettyUSD(
    trimZeros: Boolean = false,
    locale: Locale = Locale.US,
    formatter: NumberFormatting = NumberFormattingDefaults.default
): String = this.toDouble().prettyUSD(trimZeros, locale, formatter)

/** Converts this [Long] to [Double] and applies compact scaling. */
fun Long.compactText(
    fractionDigits: Int = 2,
    locale: Locale = Locale.US,
    currency: CurrencyStyle? = null,
    useGroupingWhenSuffixed: Boolean = false,
    formatter: NumberFormatting = NumberFormattingDefaults.default
): String = this.toDouble().compactText(
    fractionDigits, locale, currency, useGroupingWhenSuffixed, formatter
)
