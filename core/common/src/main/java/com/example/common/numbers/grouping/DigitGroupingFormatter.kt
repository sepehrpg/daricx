package com.example.common.numbers.grouping


import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * DigitGroupingFormatter:
 *
 * Encapsulates 3-digit grouping behavior (thousand separators) with configurable:
 * - enable/disable grouping
 * - grouping size (default 3)
 * - separator policy (use locale default or force a custom separator)
 *
 * SRP: this class configures *grouping only* and does not decide rounding or fraction digits.
 */
class DigitGroupingFormatter private constructor(
    private val enabled: Boolean,
    private val groupSize: Int,
    /**
     * Optional function that returns a grouping separator for the given locale.
     * If null, the locale's default separator is used.
     */
    private val separatorSelector: ((Locale) -> Char)?
) {
    /**
     * Applies grouping configuration to a given DecimalFormat.
     * - If grouping is disabled, sets isGroupingUsed = false.
     * - If enabled, sets the grouping size and (optionally) overrides the separator symbol.
     */
    fun applyTo(format: DecimalFormat, locale: Locale) {
        format.isGroupingUsed = enabled
        if (!enabled) return

        // Set grouping size (3 by default)
        format.groupingSize = groupSize

        // Override separator if provided; otherwise keep the locale's default
        separatorSelector?.let { selector ->
            val s = format.decimalFormatSymbols
            s.groupingSeparator = selector(locale)
            format.decimalFormatSymbols = s
        }
    }

    /**
     * Formats a long integer using this grouping policy.
     * Useful for cases below the first compact step (no suffix).
     */
    fun formatInteger(value: Long, locale: Locale = Locale.US): String {
        val nf = NumberFormat.getIntegerInstance(locale) as DecimalFormat
        applyTo(nf, locale)
        return nf.format(value)
    }

    companion object Presets {
        /** Use platform locale (default grouping size 3, default locale separator). */
        fun locale(groupSize: Int = 3) =
            DigitGroupingFormatter(enabled = true, groupSize = groupSize, separatorSelector = null)

        /** Force ASCII comma ',' regardless of locale (group size 3). */
        fun asciiComma(groupSize: Int = 3) =
            DigitGroupingFormatter(enabled = true, groupSize = groupSize, separatorSelector = { ',' })

        /** Disable grouping entirely. */
        fun disabled() =
            DigitGroupingFormatter(enabled = false, groupSize = 3, separatorSelector = null)

        /** Example: Persian thousands separator U+066C (٬). */
        fun persian(groupSize: Int = 3) =
            DigitGroupingFormatter(enabled = true, groupSize = groupSize, separatorSelector = { '\u066C' })
    }
}
