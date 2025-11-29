// com/example/network/connections/KtorNetworkModuleTest.kt
package com.example.network.connections

import com.example.common.config.AppVersionName
import com.example.network.di.KtorNetworkModule
import com.example.network.interceptor.ktor.MetadataPlugin
import com.example.network.ktor.AuthPlugin
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Test

/**
 * Tests for [KtorNetworkModule].
 *
 * Scenarios:
 * 1) Provided HttpClient installs core plugins (ContentNegotiation, HttpTimeout, HttpRequestRetry).
 * 2) Custom plugins (AuthPlugin, MetadataPlugin) are installed.
 */
class KtorNetworkModuleTest {

    private var client: HttpClient? = null

    @After
    fun tearDown() {
        client?.close()
    }

    @Test
    fun `provideKtorHttpClient installs required plugins`() {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }

        val appVersionName = "1.0.0"

        val httpClient = KtorNetworkModule.provideKtorHttpClient(
            networkJson = json,
            appVersionName = appVersionName,
        )
        client = httpClient

        // These calls throw if plugin is not installed.
        val contentNegotiation = httpClient.plugin(ContentNegotiation)
        val timeout = httpClient.plugin(HttpTimeout)
        val retry = httpClient.plugin(HttpRequestRetry)
        val auth = httpClient.plugin(AuthPlugin)
        val metadata = httpClient.plugin(MetadataPlugin)

        assertThat(contentNegotiation).isNotNull()
        assertThat(timeout).isNotNull()
        assertThat(retry).isNotNull()
        assertThat(auth).isNotNull()
        assertThat(metadata).isNotNull()
    }
}
