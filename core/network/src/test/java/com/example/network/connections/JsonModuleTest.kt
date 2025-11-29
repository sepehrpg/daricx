package com.example.network.connections


import com.example.network.di.JsonModule
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * Tests for [JsonModule].
 *
 * Scenarios:
 * - JSON config flags match the expected production configuration.
 */
class JsonModuleTest {

    @Test
    fun `providesNetworkJson uses expected flags`() {
        val json: Json = JsonModule.providesNetworkJson()

        assertThat(json.configuration.ignoreUnknownKeys).isTrue()
        assertThat(json.configuration.isLenient).isTrue()
        assertThat(json.configuration.explicitNulls).isFalse()
    }
}
