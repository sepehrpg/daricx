package com.example.model.option


enum class CryptoChart(val label: String) {
    PRICE("Price"),
    MARKETS("Markets")
}

enum class CryptoTimeRange(val label: String, val hours: Int?) {
    H1("1H", 1),
    H24("24H", 24),
    D7("7D", 24 * 7),
    M1("1M", 24 * 30),
    M3("3M", 24 * 90),
    Y1("1Y", 24 * 365),
    MAX("MAX", null);

    fun startTimestampMillis(nowMillis: Long = System.currentTimeMillis()): Long? {
        return hours?.let { nowMillis - it * 60 * 60 * 1000L }
    }
}