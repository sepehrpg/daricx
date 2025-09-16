package com.example.network.options

import com.example.model.option.TreasuryAsset


/**
 * Maps domain asset to API path value for {coin_id}.
 */
internal fun TreasuryAsset.toApiCoinId(): String = when (this) {
    TreasuryAsset.Bitcoin  -> "bitcoin"
    TreasuryAsset.Ethereum -> "ethereum"
}