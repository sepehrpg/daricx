package com.daricx.markets.ui

import androidx.lifecycle.ViewModel
import com.example.data.repository.global.GlobalRepository
import com.example.model.sort.SortKey
import com.example.model.sort.SortOption
import com.example.model.sort.SortOrder
import org.koin.android.annotation.KoinViewModel

data class MarketUiState(
    val sort: SortOption = SortOption(SortKey.MARKET_CAP, SortOrder.DESC)
)

@KoinViewModel
class MarketViewModel (
    private val repository: GlobalRepository
) : ViewModel() {

}