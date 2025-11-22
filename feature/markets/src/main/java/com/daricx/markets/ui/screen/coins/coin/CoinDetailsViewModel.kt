package com.daricx.markets.ui.screen.coins.coin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.daricx.markets.navigation.CoinDetailsScreenRoute
import com.example.common.result.AppResult
import com.example.data.repository.coins.CoinsRepository
import com.example.model.coins.CoinDetails
import com.example.model.coins.CoinHistoricalChart
import com.example.model.coins.CoinHistoricalData
import com.example.model.coins.CoinOHLCChartCandle
import com.example.model.coins.Coins
import com.example.model.coins.mapper.toFavoriteCoin
import com.example.model.option.CryptoTimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class CoinDetailsUiState(
    val coinDetails: CoinDetails? = null,
    val coinHistoricalChart: CoinHistoricalChart? = null,
    val isLoading: Boolean = false,
    val isChartLoading: Boolean = false,
    val days: String = "1",
    val selectedRange: CryptoTimeRange = CryptoTimeRange.H24,
    val error: String? = null,
    val chartError: String? = null,
    val ohlcCandles: CoinOHLCChartCandle? = null,
)

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CoinsRepository
) : ViewModel() {

    private val args: CoinDetailsScreenRoute = savedStateHandle.toRoute<CoinDetailsScreenRoute>()
    private val coinId: String = args.coinId

    private val _uiState: MutableStateFlow<CoinDetailsUiState> = MutableStateFlow(CoinDetailsUiState())
    val uiState : StateFlow<CoinDetailsUiState> = _uiState.asStateFlow()


    val favoriteIds: StateFlow<Set<String>> =
        repository
            .getFavoriteIds()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptySet()
            )


    init {
        onRefresh()
    }


    fun onRefresh(){
        getCoinDetails()
        getHistoricalChartData()
        getOhlcCandles()
    }

    fun getCoinDetails(id: String = coinId){
        Timber.d(coinId)

        viewModelScope.launch {
            repository.getCoinDetail(id).collect { result ->
                when(result){
                    is AppResult.Loading -> {
                        _uiState.update { it.copy(
                            isLoading = true
                        ) }
                    }
                    is AppResult.Success -> {
                        _uiState.update { it.copy(
                            coinDetails = result.data,
                            isLoading = false,
                        ) }

                        Timber.d(result.data.toString())
                        Timber.d(result.data.marketData?.atl.toString())
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


    fun onFavoriteClick(coin: CoinDetails?) {
        coin?.let {
            viewModelScope.launch {
                repository.toggleFavorite(coin.toFavoriteCoin())
            }
        }
    }


    fun getHistoricalChartData() {
        viewModelScope.launch {
            repository.getCoinHistoricalChart(
                coinId,
                vsCurrency = "usd",
                days = _uiState.value.days,
            ).collect { history ->

                when (history) {
                    is AppResult.Loading -> {
                        _uiState.update {
                            it.copy(
                                isChartLoading = true,
                                chartError = null
                            )
                        }
                    }

                    is AppResult.Success -> {
                        _uiState.update {
                            it.copy(
                                coinHistoricalChart = history.data,
                                isChartLoading = false,
                                chartError = null
                            )
                        }
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


    fun getOhlcCandles(){
        viewModelScope.launch {
            repository.getCoinOHLCChartCandle(
                id = coinId,
                vsCurrency = "usd",
                days = _uiState.value.days
            ).collect{
                history ->
                when (history) {
                    is AppResult.Loading -> {
                        _uiState.update {
                            it.copy(
                                isChartLoading = true,
                                chartError = null
                            )
                        }
                    }

                    is AppResult.Success -> {
                        _uiState.update {
                            it.copy(
                                ohlcCandles = history.data,
                                isChartLoading = false,
                                chartError = null
                            )
                        }
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
        getOhlcCandles()
    }
}