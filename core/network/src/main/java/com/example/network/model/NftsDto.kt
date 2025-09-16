package com.example.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** DTO for NFT item from CoinGecko API */
typealias NftsListDto = List<NftsDto>

@Serializable
data class NftsDto(
    /** Blockchain platform ID (e.g., "ethereum") */
    @SerialName("asset_platform_id")
    val assetPlatformId: String?,

    /** Contract address of the NFT collection */
    @SerialName("contract_address")
    val contractAddress: String?,

    /** Unique identifier of the NFT */
    val id: String?,

    /** Display name of the NFT (e.g., "Bored Ape Yacht Club") */
    val name: String?,

    /** Symbol/ticker of the NFT collection */
    val symbol: String?
)

