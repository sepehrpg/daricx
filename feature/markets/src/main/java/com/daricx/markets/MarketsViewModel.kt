package com.daricx.markets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.CoinMarket
import com.example.model.MarketSortColumn
import com.example.model.SortDirection
import com.example.model.SortSpec
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.ln
import kotlin.random.Random

sealed interface MarketsUiState {
    data object Loading : MarketsUiState
    data class Error(val message: String) : MarketsUiState
    data class Success(
        val items: List<CoinMarket>,
        val sort: SortSpec
    ) : MarketsUiState
}

class MarketsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<MarketsUiState>(MarketsUiState.Loading)
    val uiState: StateFlow<MarketsUiState> = _uiState

    init {
        viewModelScope.launch {
            val items = demoItems()
            val spec = SortSpec(MarketSortColumn.MARKET_CAP, SortDirection.DESC)
            _uiState.value = MarketsUiState.Success(items = sort(items, spec), sort = spec)
        }
    }

    fun onHeaderClick(column: MarketSortColumn) {
        val cur = _uiState.value as? MarketsUiState.Success ?: return
        val newDir =
            if (cur.sort.column == column)
                if (cur.sort.direction == SortDirection.DESC) SortDirection.ASC else SortDirection.DESC
            else SortDirection.DESC
        val newSpec = SortSpec(column, newDir)
        _uiState.update { cur.copy(items = sort(cur.items, newSpec), sort = newSpec) }
    }

    private fun sort(list: List<CoinMarket>, spec: SortSpec): List<CoinMarket> {
        val comp = when (spec.column) {
            MarketSortColumn.RANK -> compareBy<CoinMarket> { it.marketCapRank ?: Int.MAX_VALUE }
            MarketSortColumn.MARKET_CAP -> compareBy { it.marketCap ?: Long.MIN_VALUE }
            MarketSortColumn.PRICE -> compareBy { it.currentPrice ?: Double.MIN_VALUE }
            MarketSortColumn.CHANGE_24H -> compareBy { it.priceChangePercentage24h ?: Double.MIN_VALUE }
        }
        return if (spec.direction == SortDirection.ASC) list.sortedWith(comp)
        else list.sortedWith(comp.reversed())
    }

    /** Sample data for preview/run without API */
    private fun demoItems(): List<CoinMarket> {
        fun coin(
            id: String, rank: Int, name: String, sym: String,
            price: Double, mc: Long, ch24: Double
        ) = CoinMarket(
            ath = null, athChangePercentage = null, athDate = null,
            atl = null, atlChangePercentage = null, atlDate = null,
            circulatingSupply = null, currentPrice = price,
            fullyDilutedValuation = null, high24h = null, id = id,
            image = null, lastUpdated = null, low24h = null,
            marketCap = mc, marketCapChange24h = null, marketCapChangePercentage24h = null,
            marketCapRank = rank, maxSupply = null, name = name, priceChange24h = null,
            priceChangePercentage24h = ch24, roi = null, symbol = sym,
            totalSupply = null, totalVolume = null
        )
        return listOf(
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
            coin("pepe",30,"PEPE","PEPE",0.00001124,4_720_000_000,3.21),
            coin("shib",22,"SHIB","SHIB",0.00001318,7_760_000_000,2.26),
            coin("bonk",52,"BONK","BONK",0.00002441,1_960_000_000,2.34),
            coin("floki",77,"FLOKI","FLOKI",0.00001075,1_020_000_000,2.10),
            coin("pump",73,"PUMP","PUMP",0.003491,1_230_000_000,2.65),
            coin("jasmy",93,"JASMY","JASMY",0.01677,829_280_000,3.42),
            coin("gala",97,"GALA","GALA",0.01756,801_950_000,3.48),
        ).shuffled(Random(1))
    }


    /** Pretty price with variable precision (like many crypto UIs) */
    private fun pricePretty(price: Double): String {
        return when {
            price >= 1 -> "%,.2f".format(price)
            price >= 0.1 -> "%,.3f".format(price)
            price >= 0.01 -> "%,.4f".format(price)
            price >= 0.001 -> "%,.5f".format(price)
            else -> {
                // show significant digits for tiny prices
                val digits = (2 - ln(price).div(ln(10.0))).toInt().coerceIn(6, 10)
                "%.${digits}f".format(price)
            }
        }
    }

    /** 4.72B style */
    private fun compactNumber(n: Double): String {
        val abs = kotlin.math.abs(n)
        return when {
            abs >= 1_000_000_000 -> "%,.2fB".format(n / 1_000_000_000)
            abs >= 1_000_000 -> "%,.2fM".format(n / 1_000_000)
            abs >= 1_000 -> "%,.2fK".format(n / 1_000)
            else -> "%,.0f".format(n)
        }
    }
}
