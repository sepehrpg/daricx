package com.daricx.markets.ui.screen.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.result.AppError
import com.example.common.result.AppResult
import com.example.data.repository.trending.TrendingRepository
import com.example.model.Trending
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber


data class TrendingUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val trending: Trending? = null,
    val error: AppError? = null
)

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val trendingRepository: TrendingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrendingUiState(isLoading = false))
    val uiState: StateFlow<TrendingUiState> = _uiState.asStateFlow()

    init {
        Timber.d("init TrendingViewModel")
        // Option A) auto-load on start:
        getTrending()
    }

    /**
     * Loads trending data. If [refresh] is true, shows pull-to-refresh spinner
     * instead of the full-screen loading.
     */
    fun getTrending(refresh: Boolean = false) {
        viewModelScope.launch {
            trendingRepository.getTrending().collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = if (!refresh) true else it.isLoading,
                                isRefreshing = if (refresh) true else it.isRefreshing,
                                error = null // clear previous error on new load
                            )
                        }
                    }
                    is AppResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                trending = result.data,
                                error = null
                            )
                        }
                        Timber.d(result.data.toString())
                    }
                    is AppResult.Error -> {
                        Timber.w(result.error.cause, "Trending load failed: ${result.error}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = result.error
                            )
                        }
                    }
                }
            }
        }
    }

    /** Convenience for pull-to-refresh gestures. */
    fun refresh() = getTrending(refresh = true)

    /** Retry after an error. */
    fun retry() = getTrending(refresh = false)
}
