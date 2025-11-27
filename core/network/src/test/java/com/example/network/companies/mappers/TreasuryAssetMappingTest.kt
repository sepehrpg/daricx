package com.example.network.companies.mappers

import com.example.model.option.TreasuryAsset
import com.example.network.options.toApiCoinId
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * TreasuryAssetMappingTest
 *
 * Test Goal:
 * - Verify TreasuryAsset enum maps to correct {coin_id} strings for API path.
 *
 * Scenarios:
 * 1) TreasuryAsset.Bitcoin → "bitcoin".
 * 2) TreasuryAsset.Ethereum → "ethereum".
 */
@RunWith(Parameterized::class)
class TreasuryAssetMappingTest(
    private val input: TreasuryAsset,
    private val expected: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0} -> {1}")
        fun data() = listOf(
            arrayOf(TreasuryAsset.Bitcoin, "bitcoin"),
            arrayOf(TreasuryAsset.Ethereum, "ethereum")
        )
    }

    @Test
    fun `maps to coin id`() {
        Truth.assertThat(input.toApiCoinId()).isEqualTo(expected)
    }
}