package com.example.network.options

import com.example.model.option.TreasuryAsset



// if use this override fun toString(): String = value in enum class don't need to this mapper
/**
 * Maps domain asset to API path value for {coin_id}.
 */
fun TreasuryAsset.toApiCoinId(): String = when (this) {
    TreasuryAsset.Bitcoin  -> "bitcoin"
    TreasuryAsset.Ethereum -> "ethereum"
}