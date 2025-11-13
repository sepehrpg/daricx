package com.example.network.connections


import androidx.test.core.app.ApplicationProvider
import android.content.Context
import com.example.network.NetworkConfig
import com.example.network.di.RetrofitModule
import com.example.network.interceptor.AuthInterceptor
import com.example.network.interceptor.MetadataInterceptor
import com.example.network.interceptor.RetryInterceptor
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for DI wiring in [RetrofitModule].
 *
 * Test Goals:
 * - JSON config flags.
 * - OkHttpClient interceptor ordering and timeouts.
 * - EventListener factory wiring.
 * - Retrofit baseUrl and callFactory.
 *
 * Scenarios:
 * 1) providesNetworkJson -> flags as configured.
 * 2) provideOkHttp -> [Auth, Metadata, Retry, (debug-only)] in order; timeouts set.
 * 3) provideEventListenerFactory -> used by client.
 * 4) provideRetrofitInstance -> baseUrl == NetworkConfig.BASE_URL.
 */
@RunWith(RobolectricTestRunner::class)
class RetrofitModuleTest {

    @Test
    fun `providesNetworkJson flags`() {
        val json: Json = RetrofitModule.providesNetworkJson()
        // These are the flags you configured in the module
        assertThat(json.configuration.ignoreUnknownKeys).isTrue()
        assertThat(json.configuration.isLenient).isTrue()
        assertThat(json.configuration.explicitNulls).isFalse()
    }

    @Test
    fun `provideOkHttp ordering and timeouts`() {
        val ctx: Context = ApplicationProvider.getApplicationContext()
        val chucker = RetrofitModule.provideChucker(ctx)
        val json = RetrofitModule.providesNetworkJson()
        val meta = RetrofitModule.provideMetadataInterceptor(json, "1.0.0")
        val auth = RetrofitModule.provideAuthInterceptor()
        val retry = RetrofitModule.provideRetryInterceptor()
        val bodyLogger = RetrofitModule.provideBodyLoggingInterceptor()
        val evtFactory: EventListener.Factory = RetrofitModule.provideEventListenerFactory()

        val client: OkHttpClient = RetrofitModule.provideOkHttp(
            chucker = chucker,
            auth = auth,
            meta = meta,
            retry = retry,
            bodyLogger = bodyLogger,
            eventListenerFactory = evtFactory
        )

        val ints: List<Interceptor> = client.interceptors
        // First three MUST be auth, meta, retry (debug interceptors may follow in debug builds)
        assertThat(ints[0]).isInstanceOf(AuthInterceptor::class.java)
        assertThat(ints[1]).isInstanceOf(MetadataInterceptor::class.java)
        assertThat(ints[2]).isInstanceOf(RetryInterceptor::class.java)

        // Timeouts
        val timeoutMillis = com.example.network.NetworkConfig.TIMEOUT_SECONDS * 1000L
        assertThat(client.connectTimeoutMillis.toLong()).isEqualTo(timeoutMillis)
        assertThat(client.readTimeoutMillis.toLong()).isEqualTo(timeoutMillis)
        assertThat(client.writeTimeoutMillis.toLong()).isEqualTo(timeoutMillis)
    }

    @Test
    fun `provideRetrofitInstance baseUrl`() {
        val json = RetrofitModule.providesNetworkJson()
        val ok = OkHttpClient()

        val lazyOk = object : dagger.Lazy<OkHttpClient> {
            override fun get(): OkHttpClient = ok
        }

        val retrofit = RetrofitModule.provideRetrofitInstance(json, lazyOk)
        assertThat(retrofit.baseUrl().toString()).isEqualTo(NetworkConfig.BASE_URL)
    }
}
