package com.example.common.format

import java.util.Locale
import kotlin.math.abs
import kotlin.math.ln

object NumberFormatters {

    /** Pretty price with variable precision (like many crypto UIs) */
    @JvmStatic
    fun pricePretty(
        price: Double,
        currencySymbol: String = "$",
        locale: Locale = Locale.US
    ): String {
        val formatted = when {
            price >= 1 -> String.format(locale, "%,.2f", price)
            price >= 0.1 -> String.format(locale, "%,.3f", price)
            price >= 0.01 -> String.format(locale, "%,.4f", price)
            price >= 0.001 -> String.format(locale, "%,.5f", price)
            price > 0.0 -> {
                // significant digits for tiny prices
                val digits = (2 - ln(price) / ln(10.0)).toInt().coerceIn(6, 10)
                String.format(locale, "%,.${digits}f", price)
            }
            price == 0.0 -> "0"
            else -> String.format(locale, "%,.6f", price)
        }
        return currencySymbol + formatted
    }

    /** 4.72B / 15.3M / 532.0K style */
    @JvmStatic
    fun compactNumber(
        n: Double?,
        locale: Locale = Locale.US,
        fractionDigits: Int = 2
    ): String {
        val v = n ?: return "—"
        val a = abs(v)
        val fmt = "%,.${fractionDigits}f"
        return when {
            a >= 1_000_000_000 -> String.format(locale, fmt + "B", v / 1_000_000_000.0)
            a >= 1_000_000     -> String.format(locale, fmt + "M", v / 1_000_000.0)
            a >= 1_000         -> String.format(locale, fmt + "K", v / 1_000.0)
            else               -> String.format(locale, "%,d", v.toLong())
        }
    }

}