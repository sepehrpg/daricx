package com.example.network.options

import com.example.model.sort.ExchangeTickersOrder

fun ExchangeTickersOrder.toApiOrderParam(): String = when (this) {
    ExchangeTickersOrder.TRUST_SCORE_DESC         -> "trust_score_desc"
    ExchangeTickersOrder.TRUST_SCORE_ASC          -> "trust_score_asc"
    ExchangeTickersOrder.VOLUME_DESC              -> "volume_desc"
    ExchangeTickersOrder.VOLUME_ASC              -> "volume_asc"
    ExchangeTickersOrder.MARKET_CAP_ASC               -> "market_cap_asc"
    ExchangeTickersOrder.MARKET_CAP_DESC               -> "market_cap_desc"
    ExchangeTickersOrder.BASE_TARGET               -> "base_target"
}
