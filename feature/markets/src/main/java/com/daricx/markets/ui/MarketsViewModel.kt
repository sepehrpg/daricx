package com.daricx.markets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.daricx.markets.utils.isServerSortSupported
import com.daricx.markets.utils.mapSortSpecToServerOrderOrNull
import com.example.data.repository.CoinRepository
import com.example.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class MarketsUiState(
    val sort: SortSpec = SortSpec(MarketSortColumn.MARKET_CAP, SortDirection.DESC)
)

@HiltViewModel
class MarketsViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {

    private val vsCurrency = "usd"
    private val _uiState = MutableStateFlow(MarketsUiState())
    val uiState: StateFlow<MarketsUiState> = _uiState

    val pagedCoins = _uiState.flatMapLatest { state ->
        val spec = state.sort
        val serverOrder = mapSortSpecToServerOrderOrNull(spec)

        val pageTransform: ((List<CoinMarket>) -> List<CoinMarket>)? =
            if (isServerSortSupported(spec)) null
            else when (spec.column) {
                MarketSortColumn.PRICE -> {
                    if (spec.direction == SortDirection.ASC)
                        { list -> list.sortedBy { it.currentPrice ?: Double.MIN_VALUE } }
                    else
                        { list -> list.sortedByDescending { it.currentPrice ?: Double.MIN_VALUE } }
                }
                MarketSortColumn.CHANGE_24H -> {
                    if (spec.direction == SortDirection.ASC)
                        { list -> list.sortedBy { it.priceChangePercentage24h ?: Double.MIN_VALUE } }
                    else
                        { list -> list.sortedByDescending { it.priceChangePercentage24h ?: Double.MIN_VALUE } }
                }
                else -> null
            }

        repository.getCoinMarketsPaged(
            vsCurrency = vsCurrency,
            pageSize = 50,
            order = serverOrder ?: "market_cap_desc",
            sparkline = true,
            priceChangePercentage = "1h,24h,7d",
            pageTransform = pageTransform
        )
    }.cachedIn(viewModelScope)

    fun onHeaderClick(column: MarketSortColumn) {
        val cur = _uiState.value.sort
        val newDir =
            if (cur.column == column) {
                if (cur.direction == SortDirection.DESC) SortDirection.ASC else SortDirection.DESC
            } else SortDirection.DESC
        _uiState.value = _uiState.value.copy(sort = SortSpec(column, newDir))
    }
}
