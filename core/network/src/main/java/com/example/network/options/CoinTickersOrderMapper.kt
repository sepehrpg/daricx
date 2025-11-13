package com.example.network.options

import com.example.model.sort.CoinTickersOrder

// if use this override fun toString(): String = value in enum class don't need to this mapper
fun CoinTickersOrder.toApiOrderParam(): String = when (this) {
    CoinTickersOrder.TRUST_SCORE_DESC         -> "trust_score_desc"
    CoinTickersOrder.TRUST_SCORE_ASC          -> "trust_score_asc"
    CoinTickersOrder.VOLUME_DESC              -> "volume_desc"
    CoinTickersOrder.VOLUME_ASC               -> "volume_asc"
}

