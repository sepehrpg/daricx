package com.daricx.markets.ui.screen.nfts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.repository.nfts.NftsRepository
import com.example.model.sort.NftsSort
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class NftsUiState(
    val sort: NftsSort = NftsSort.H24VolumeUsdAsc
)

@HiltViewModel
class NftsViewModel @Inject constructor(
    private val repository: NftsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NftsUiState())
    val uiState: StateFlow<NftsUiState> = _uiState.asStateFlow()

    val pagedNfts = uiState
        .map { it.sort }
        .distinctUntilChanged()
        .onEach { Timber.d("Nfts sort changed: %s", it) }
        .flatMapLatest { order ->
            repository.getNftsPaged(
                pageSize = 50,
                order = order
            )
        }
        .onEach { Timber.d("New PagingData emitted for sort=%s", uiState.value.sort) }
        .cachedIn(viewModelScope)

    init {
        Timber.d("Init NftsViewModel")
        viewModelScope.launch {
            pagedNfts.collect {
                Timber.d("PagingData emission received.")
            }
        }
    }
}
