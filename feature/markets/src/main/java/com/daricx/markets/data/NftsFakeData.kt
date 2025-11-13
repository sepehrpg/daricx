package com.daricx.markets.data


import com.example.model.nfts.Nfts

/**
 * Provides fake data for [Nfts] domain model.
 * Useful for UI previews, testing and offline development.
 */
object NftsFakeData {

    /**
     * Generate a single fake [Nfts] object
     */
    fun generateFakeNft(index: Int = 0): Nfts {
        return Nfts(
            assetPlatformId = "ethereum",
            contractAddress = "0xFAKE${index}1234567890abcdef",
            id = "nft-$index",
            name = "NFT Collection $index",
            symbol = "NFT$index"
        )
    }

    /**
     * Generate a list of fake NFTs
     */
    val nfts: List<Nfts> = List(12) { i ->
        generateFakeNft(i)
    }
}
