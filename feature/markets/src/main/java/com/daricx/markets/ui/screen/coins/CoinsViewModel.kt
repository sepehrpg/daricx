package com.daricx.markets.ui.screen.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.repository.coins.CoinsRepository
import com.example.model.coins.Coins
import com.example.model.coins.mapper.toFavoriteCoin
import com.example.model.sort.CoinsSort
import com.example.model.sort.SortKey
import com.example.model.sort.SortOrder
import com.example.model.sort.SortOption
import com.example.model.sort.isServerSupported
import com.example.model.sort.toCoinsSortOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject



data class CoinsUiState(
    val sort: SortOption = SortOption(SortKey.MARKET_CAP, SortOrder.DESC)
)

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val repository: CoinsRepository
) : ViewModel() {

    init {
        Timber.d("COINS_VM_START")
    }

    private val vsCurrency = "usd"
    private val _uiState = MutableStateFlow(CoinsUiState())
    val uiState: StateFlow<CoinsUiState> = _uiState.asStateFlow()

    val pagedCoins = _uiState.flatMapLatest { state ->
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

        repository.getCoinMarketsPaged(
            vsCurrency = vsCurrency,
            pageSize = 50,
            order = serverSort ?: CoinsSort.MarketCapDesc,
            sparkline = true,
            priceChangePercentage = "1h,24h,7d",
            pageTransform = pageTransform
        )
    }.cachedIn(viewModelScope)


    val favoriteIds: StateFlow<Set<String>> =
        repository
            .getFavoriteIds()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptySet()
            )

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

