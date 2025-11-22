package com.example.common.numbers.adapter

import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.facade.NumberFormattingDefaults
import java.util.Locale

object NumberFormatterAdapter {

    private val facade get() = NumberFormattingDefaults.defaultWithAutoPrecision

    /** Pretty price with variable precision (backward compatible) */
    @JvmStatic
    fun pricePretty(
        price: Double,
        currencySymbol: String = "$",
        revert: Boolean = false,
        locale: Locale = Locale.US
    ): String {
        val style = if (revert)
            CurrencyStyle(
                symbol = currencySymbol,
                position = CurrencyStyle.Position.SUFFIX,
                name = "usd",
                withSpace = false
            )
        else
            CurrencyStyle(
                symbol = currencySymbol,
                name = "usd",
                position = CurrencyStyle.Position.PREFIX,
                withSpace = false
            )

        return facade.pricePretty(
            price = price,
            currency = style,
            trimZeros = false,
            locale = locale
        )
    }

    /** 4.72B / 15.3M / 532K style (backward compatible) */
    @JvmStatic
    fun compactNumber(
        n: Double?,
        locale: Locale = Locale.US,
        fractionDigits: Int = 2
    ): String {
        return facade.compactNumber(
            n = n,
            locale = locale,
            fractionDigits = fractionDigits,
            currency = null
        )
    }
}