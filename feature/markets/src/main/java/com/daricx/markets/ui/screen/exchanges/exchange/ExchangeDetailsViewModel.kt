package com.daricx.markets.ui.screen.exchanges.exchange

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.daricx.markets.navigation.CoinDetailsScreenRoute
import com.daricx.markets.navigation.ExchangeDetailsScreenRoute
import com.example.common.result.AppResult
import com.example.data.repository.coins.CoinsRepository
import com.example.data.repository.exchanges.ExchangesRepository
import com.example.model.coins.CoinDetails
import com.example.model.exchanges.ExchangeDetail
import com.example.model.exchanges.ExchangeVolumeChart
import com.example.model.option.CryptoTimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class ExchangeDetailsUiState(
    val exchangeDetails: ExchangeDetail? = null,
    val exchangeVolumeChart: ExchangeVolumeChart? = null,
    val selectedRange: CryptoTimeRange = CryptoTimeRange.H24,
    val isLoading: Boolean = false,
    val isChartLoading: Boolean = false,
    val error: String? = null,
    val chartError: String? = null,
    val days: String = "1",
)

@HiltViewModel
class ExchangeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ExchangesRepository
) : ViewModel() {
    private val args: ExchangeDetailsScreenRoute = savedStateHandle.toRoute<ExchangeDetailsScreenRoute>()
    private val exchangeId: String = args.exchangeId

    private val _uiState: MutableStateFlow<ExchangeDetailsUiState> = MutableStateFlow(ExchangeDetailsUiState())
    val uiState : StateFlow<ExchangeDetailsUiState> = _uiState.asStateFlow()
    init {
        onRefresh()
    }


    fun onRefresh(){
        getExchangeDetails()
        getHistoricalChartData()
    }


    fun getExchangeDetails(id: String = exchangeId){
        Timber.d(exchangeId)
        viewModelScope.launch {
            repository.getExchangeById(id).collect { result ->
                when(result){
                    is AppResult.Loading -> {
                        _uiState.update { it.copy(
                            isLoading = true
                        ) }
                    }
                    is AppResult.Success -> {
                        _uiState.update { it.copy(
                            exchangeDetails = result.data,
                            isLoading = false,
                        ) }

                        Timber.d(result.data.toString())
                    }
                    is AppResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.error.message ?: "Unknown error"
                            )}
                        Timber.e(result.error.message )
                    }
                }
            }
        }
    }

    fun getHistoricalChartData(){
        viewModelScope.launch {
            repository.getExchangeVolumeChart(
                id = exchangeId,
                days = _uiState.value.days,
            ).collect { history->
                when(history){
                    is AppResult.Loading -> {
                        _uiState.update {
                            it.copy(
                                isChartLoading = true,
                                chartError = null
                            )
                        }
                    }
                    is AppResult.Success -> {
                        _uiState.update { it.copy(
                            exchangeVolumeChart = history.data,
                            isChartLoading = false,
                            chartError = null
                        ) }
                        Timber.d(history.data.toString())
                    }
                    is AppResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isChartLoading = false,
                                chartError = history.error.message ?: "Unknown chart error"
                            )
                        }
                    }
                }

            }
        }
    }


    fun onTimeRangeSelected(range: CryptoTimeRange) {
        _uiState.update {
            it.copy(
                selectedRange = range,
                days = range.toDaysParam()
            )
        }

        getHistoricalChartData()
    }
}