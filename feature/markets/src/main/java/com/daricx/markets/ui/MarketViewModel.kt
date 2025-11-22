package com.daricx.markets.ui

import androidx.lifecycle.ViewModel
import com.example.data.repository.coins.CoinsRepository
import com.example.data.repository.global.GlobalRepository
import com.example.model.sort.SortKey
import com.example.model.sort.SortOption
import com.example.model.sort.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class MarketUiState(
    val sort: SortOption = SortOption(SortKey.MARKET_CAP, SortOrder.DESC)
)

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val repository: GlobalRepository
) : ViewModel() {

}