package com.daricx.markets.ui.screen.coins.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.result.AppResult
import com.example.data.repository.coins.CoinsRepository
import com.example.model.coins.CoinDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CoinDetailsUiState(
    val coinDetails: CoinDetails? = null,
    val isLoading: Boolean = false,
)

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val repository: CoinsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<CoinDetailsUiState> = MutableStateFlow(CoinDetailsUiState())
    val uiState : StateFlow<CoinDetailsUiState> = _uiState.asStateFlow()


    init {
        getCoinDetails()
    }


    fun getCoinDetails(){
        viewModelScope.launch {
            repository.getCoinDetail("bitcoin").collect { result ->
                when(result){
                    is AppResult.Loading -> {
                        _uiState.update { it.copy(
                            isLoading = true
                        ) }
                    }
                    is AppResult.Success -> {
                        _uiState.update { it.copy(
                            coinDetails = result.data
                        ) }
                    }
                    is AppResult.Error -> {

                    }
                }
            }
        }
    }

}