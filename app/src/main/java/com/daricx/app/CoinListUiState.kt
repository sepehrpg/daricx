package com.daricx.app


sealed interface CoinListUiState {
    data object Loading : CoinListUiState
    data class Success(val coins: List<Coin>) : CoinListUiState
    data class Error(val message: String) : CoinListUiState
}