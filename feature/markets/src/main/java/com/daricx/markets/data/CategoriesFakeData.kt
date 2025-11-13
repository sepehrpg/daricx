package com.daricx.markets.data

import com.example.model.Categories

/**
 * Provides fake data for [Categories] domain model.
 * Useful for UI previews, tests, and offline prototyping.
 */
object CategoriesFakeData {

    /**
     * Generate a single fake [Categories] object
     */
    fun generateFakeCategory(index: Int = 0): Categories {
        return Categories(
            id = "cat-$index",
            name = "Category $index",
            marketCap = 1_000_000_000.0 + (index * 50_000_000),
            marketCapChange24h = (-5..5).random().toDouble(), // example +/- percentage
            volume24h = 200_000_000.0 + (index * 10_000_000),
            top3Coins = listOf(
                "https://dummyimage.com/64x64/0${index}0/fff&text=C1",
                "https://dummyimage.com/64x64/0${index}1/fff&text=C2",
                "https://dummyimage.com/64x64/0${index}2/fff&text=C3"
            ),
            top3CoinsId = listOf("coin-${index}a", "coin-${index}b", "coin-${index}c"),
            content = "Description for Category $index",
            updatedAt = "2025-10-02T12:00:00Z"
        )
    }

    /**
     * Generate a list of fake [Categories]
     */
    val categories: List<Categories> = List(10) { i ->
        generateFakeCategory(i)
    }
}
