package com.example.model.nfts


/**
 * Domain model for NFT item
 */
data class Nfts(
    /** Blockchain platform ID (e.g., "ethereum") */
    val assetPlatformId: String?,

    /** Contract address of the NFT collection */
    val contractAddress: String?,

    /** Unique identifier of the NFT */
    val id: String?,

    /** Display name of the NFT (e.g., "Bored Ape Yacht Club") */
    val name: String?,

    /** Symbol/ticker of the NFT collection */
    val symbol: String?
)


