package com.example.network.categories

import com.example.network.model.CategoriesListDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * CategoriesSerializationTest
 *
 * Test Goal:
 * - Ensure CategoriesListDto can be deserialized from JSON with unknown fields present.
 *
 * Scenarios:
 * 1) Deserialize array with all known fields + one unknown field.
 */
class CategoriesSerializationTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test fun `deserializes with unknown fields ignored`() {
        val dto: CategoriesListDto = json.decodeFromString(SampleJsonCategories.categoriesResponse)
        assertThat(dto).hasSize(1)
        assertThat(dto.first().updatedAt).isEqualTo("2025-09-01T00:00:00Z")
        assertThat(dto.first().top3CoinsId?.contains("ethereum")).isTrue()
    }
}