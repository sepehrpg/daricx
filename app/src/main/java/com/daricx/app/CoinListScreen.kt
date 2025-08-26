package com.daricx.app
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel() // Get the ViewModel instance
) {
    // Collect the state in a lifecycle-aware manner
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Display UI based on the current state
    when (val state = uiState) {
        is CoinListUiState.Loading -> {
            // Show a loading indicator
            CircularProgressIndicator()
        }
        is CoinListUiState.Success -> {
            // Show the list of coins
           /* LazyColumn {
                items(state.coins) { coin ->
                    Text(text = "${coin.name} (${coin.symbol})")
                }
            }*/
        }
        is CoinListUiState.Error -> {
            // Show an error message
            Text(text = state.message)
        }
    }
}