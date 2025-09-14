package com.example.data.repository.settings

import app.cash.turbine.test
import com.example.datastore.SettingsDataSource
import com.example.model.settings.AppCurrency
import com.example.model.settings.AppLanguage
import com.example.model.settings.AppThemeOption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsRepositoryImplTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: SettingsRepository
    private lateinit var scope: CoroutineScope

    @BeforeEach
    fun setUp() {
        val fs = FakeFileSystem()
        scope = CoroutineScope(dispatcher + Job())
        val dataStore = PreferenceDataStoreFactory.createWithPath(
            scope = scope,
            fileSystem = fs,
            produceFile = { "/settings.preferences_pb".toPath() }
        )
        val ds = SettingsDataSource(dataStore)
        repository = SettingsRepositoryImpl(ds)
    }

    @AfterEach
    fun tearDown() {
        scope.cancel()
    }

    @Test
    fun `settings emits defaults`() = runTest(dispatcher) {
        repository.settings.test {
            val item = awaitItem()
            assertThat(item.theme).isEqualTo(AppThemeOption.System)
            assertThat(item.dynamicColor).isTrue()
            assertThat(item.language).isEqualTo(AppLanguage.English)
            assertThat(item.currency).isEqualTo(AppCurrency.USD)
            assertThat(item.forceRtl).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setTheme updates theme`() = runTest(dispatcher) {
        repository.setTheme(AppThemeOption.Dark)
        advanceUntilIdle()
        assertThat(repository.settings.first().theme).isEqualTo(AppThemeOption.Dark)
    }

    @Test
    fun `setDynamicColor updates flag`() = runTest(dispatcher) {
        repository.setDynamicColor(false)
        advanceUntilIdle()
        assertThat(repository.settings.first().dynamicColor).isFalse()
    }

    @Test
    fun `setLanguage updates language`() = runTest(dispatcher) {
        repository.setLanguage(AppLanguage.Persian)
        advanceUntilIdle()
        assertThat(repository.settings.first().language).isEqualTo(AppLanguage.Persian)
    }

    @Test
    fun `setCurrency updates currency`() = runTest(dispatcher) {
        repository.setCurrency(AppCurrency.BTC)
        advanceUntilIdle()
        assertThat(repository.settings.first().currency).isEqualTo(AppCurrency.BTC)
    }

    @Test
    fun `setForceRtl updates flag`() = runTest(dispatcher) {
        repository.setForceRtl(true)
        advanceUntilIdle()
        assertThat(repository.settings.first().forceRtl).isTrue()
    }
}
