package com.daricx.markets.ui.screen.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repository.coins.CoinsRepository
import com.example.model.coins.Coins
import com.example.model.coins.mapper.toFavoriteCoin
import com.example.model.sort.CoinsSort
import com.example.model.sort.SortKey
import com.example.model.sort.SortOption
import com.example.model.sort.SortOrder
import com.example.model.sort.isServerSupported
import com.example.model.sort.toCoinsSortOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

data class WatchlistUiState(
    val sort: SortOption = SortOption(SortKey.MARKET_CAP, SortOrder.DESC)
)

@KoinViewModel
class WatchlistViewModel (
    private val repository: CoinsRepository
) : ViewModel() {

    init {
        Timber.d("WATCHLIST_VM_START")
    }

    private val vsCurrency = "usd"
    private val _uiState = MutableStateFlow(WatchlistUiState())
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()


    val favoriteIds: StateFlow<Set<String>> =
        repository
            .getFavoriteIds()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptySet()
            )

    val pagedCoinsFav = combine(
        _uiState,
        favoriteIds
    ) { state, favIds ->
        state to favIds
    }.flatMapLatest { (state, favIds) ->

        if (favIds.isEmpty()) {
            flowOf<PagingData<Coins>>(PagingData.empty())
        } else {
            val spec = state.sort
            val serverSort: CoinsSort? = spec.toCoinsSortOrNull()

            val pageTransform: ((List<Coins>) -> List<Coins>)? =
                if (spec.isServerSupported()) null
                else when (spec.sortKey) {
                    SortKey.PRICE -> {
                        if (spec.sortOrder == SortOrder.ASC)
                            { list -> list.sortedBy { it.currentPrice ?: Double.MIN_VALUE } }
                        else
                            { list -> list.sortedByDescending { it.currentPrice ?: Double.MIN_VALUE } }
                    }
                    SortKey.CHANGE_24H -> {
                        if (spec.sortOrder == SortOrder.ASC)
                            { list -> list.sortedBy { it.priceChangePercentage24h ?: Double.MIN_VALUE } }
                        else
                            { list -> list.sortedByDescending { it.priceChangePercentage24h ?: Double.MIN_VALUE } }
                    }
                    else -> null
                }

            val idsParam: String = favIds.joinToString(",")

            repository.getCoinMarketsPaged(
                vsCurrency = vsCurrency,
                ids = idsParam,
                pageSize = 50,
                order = serverSort ?: CoinsSort.MarketCapDesc,
                sparkline = true,
                priceChangePercentage = "1h,24h,7d",
                pageTransform = pageTransform
            )
        }
    }.cachedIn(viewModelScope)


    fun onHeaderClick(column: SortKey) {
        val cur = _uiState.value.sort
        val newDir =
            if (cur.sortKey == column) {
                if (cur.sortOrder == SortOrder.DESC) SortOrder.ASC else SortOrder.DESC
            } else SortOrder.DESC
        _uiState.value = _uiState.value.copy(sort = SortOption(column, newDir))
    }

    fun onFavoriteClick(coin: Coins) {
        viewModelScope.launch {
            repository.toggleFavorite(coin.toFavoriteCoin())
        }
    }
}