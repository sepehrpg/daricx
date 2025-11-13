package com.daricx.markets.ui.screen.categories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.result.AppError
import com.example.common.result.AppResult
import com.example.data.repository.categories.CategoriesRepository
import com.example.model.Categories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber


data class CategoriesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val categories: List<Categories?>? = null,
    val error: AppError? = null
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val trendingRepository: CategoriesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState(isLoading = false))
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        Timber.d("init CategoriesViewModel")
        // Option A) auto-load on start:
        getCategories()

    }


    fun getCategories(refresh: Boolean = false) {
        viewModelScope.launch {
            trendingRepository.getCategories().collect { result ->
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
                                categories = result.data,
                                error = null
                            )
                        }
                        Timber.d("getCategories : ${_uiState.value.categories?.toString()}")
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
    fun refresh() = getCategories(refresh = true)

    /** Retry after an error. */
    fun retry() = getCategories(refresh = false)
}
