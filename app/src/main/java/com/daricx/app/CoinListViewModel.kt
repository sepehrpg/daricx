package com.daricx.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    coinRepository: CoinRepository // Hilt provides the repository implementation
) : ViewModel() {

    // This StateFlow will be observed by the UI.
    val uiState: StateFlow<CoinListUiState> =
    // Call the repository function that returns a Flow
        coinRepository.getCoins()
            .map<List<Coin>, CoinListUiState> { coins ->
                // If the flow emits a list, wrap it in a Success state
                CoinListUiState.Success(coins)
            }
            .onStart {
                // Before the flow starts, emit a Loading state
                emit(CoinListUiState.Loading)
            }
            .catch { throwable ->
                // If an error occurs in the flow, emit an Error state
                emit(CoinListUiState.Error(throwable.message ?: "An unknown error occurred"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CoinListUiState.Loading
            )
}