package com.daricx.markets.ui.screen.exchanges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repository.exchanges.ExchangesRepository
import com.example.model.exchanges.Exchanges
import kotlinx.coroutines.flow.*
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

data class ExchangesUiState(
    val test:String = "Test"
)

@KoinViewModel
class ExchangesViewModel (
    private val repository: ExchangesRepository
) : ViewModel() {

    init {
        Timber.d("Exchanges_VM_START")
    }

    private val _uiState = MutableStateFlow(ExchangesUiState())
    val uiState: StateFlow<ExchangesUiState> = _uiState.asStateFlow()

    val pagedExchanges: Flow<PagingData<Exchanges>> =
        repository.getExchangesPaged(
            pageSize = 50,
        ).cachedIn(viewModelScope)

    init {
        Timber.i("init ExchangesViewModel")
    }


}


fun sepehr(){
    while (true){
        val item = "Sepehr"
        
    }
}
