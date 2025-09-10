package com.example.model.settings

sealed interface AppCurrency {
    val code: String
    val symbol: String? get() = null

    data object USD : AppCurrency {
        override val code = "USD"
        override val symbol = "$"
    }

    data object BTC : AppCurrency {
        override val code = "BTC"
        override val symbol = "₿"
    }

    data class Custom(override val code: String, override val symbol: String? = null) : AppCurrency

    companion object {
        fun from(code: String?): AppCurrency = when (code?.uppercase()) {
            USD.code -> USD
            BTC.code -> BTC
            null, "" -> USD
            else -> Custom(code)
        }

        val supported: List<AppCurrency> = listOf(USD, BTC)
    }
}

val AppCurrency.displayLabel: String get() =
    symbol?.let { "$code ($it)" } ?: code