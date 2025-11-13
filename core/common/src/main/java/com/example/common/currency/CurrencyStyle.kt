package com.example.common.currency

/**
 * CurrencyStyle defines how a currency/token mark is attached to the numeric string.
 */
data class CurrencyStyle(
    val symbol: String,                    // e.g., "$", "€", "₿", "BTC", "﷼"
    val name: String,
    val position: Position = Position.PREFIX,
    val withSpace: Boolean = false
) {
    enum class Position { PREFIX, SUFFIX }

    /** Decorates a pre-formatted number string with currency symbol using the configured placement. */
    fun decorate(number: String): String =
        when (position) {
            Position.PREFIX -> if (withSpace) "$symbol $number" else "$symbol$number"
            Position.SUFFIX -> if (withSpace) "$number $symbol" else "$number$symbol"
        }

    companion object Presets {
        fun usd() = CurrencyStyle(symbol = "$", position = Position.PREFIX, name = "usd", withSpace = false)
        fun euro() = CurrencyStyle(symbol = "€", position = Position.PREFIX, name = "eru", withSpace = false)
        fun btcPrefix() = CurrencyStyle(symbol = "₿", position = Position.PREFIX, name = "btc", withSpace = false)
        fun btcSuffixCode() = CurrencyStyle(symbol = "BTC", position = Position.SUFFIX, name = "btc", withSpace = true)
        fun rial() = CurrencyStyle(symbol = "﷼", position = Position.SUFFIX, name = "rial", withSpace = true)
        fun empty() = CurrencyStyle(symbol = "", position = Position.PREFIX, name = "", withSpace = false)
    }
}