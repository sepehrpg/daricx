package com.example.network.categories.mapper

import com.example.model.sort.CategoriesSort
import com.example.network.options.toApiOrderParam
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * CategoriesSortMappingTest
 *
 * Test Goal:
 * - Verify all CategoriesSort entries map to correct API "order" values via toApiOrderParam().
 *
 * Scenarios:
 * - One parameterized case per enum value.
 */
@RunWith(Parameterized::class)
class CategoriesSortMappingTest(
    private val input: CategoriesSort,
    private val expected: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0} -> {1}")
        fun data() = listOf(
            arrayOf(CategoriesSort.MarketCapDesc,          "market_cap_desc"),
            arrayOf(CategoriesSort.MarketCapAsc,           "market_cap_asc"),
            arrayOf(CategoriesSort.NameDesc,               "name_desc"),
            arrayOf(CategoriesSort.NameAsc,                "name_asc"),
            arrayOf(CategoriesSort.MarketCapChange24hDesc, "market_cap_change_24h_desc"),
            arrayOf(CategoriesSort.MarketCapChange24hAsc,  "market_cap_change_24h_asc"),
        )
    }

    @Test
    fun `maps enum to correct api string`() {
        Truth.assertThat(input.toApiOrderParam()).isEqualTo(expected)
    }
}