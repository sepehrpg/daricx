package com.example.data.testing

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.model.CoinMarket
import com.example.model.Exchange

/** Utility objects for testing Paging flows. */
object PagingTestUtils {
    val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    object CoinMarketDiff : DiffUtil.ItemCallback<CoinMarket>() {
        override fun areItemsTheSame(oldItem: CoinMarket, newItem: CoinMarket): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CoinMarket, newItem: CoinMarket): Boolean = oldItem == newItem
    }

    object ExchangeDiff : DiffUtil.ItemCallback<Exchange>() {
        override fun areItemsTheSame(oldItem: Exchange, newItem: Exchange): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Exchange, newItem: Exchange): Boolean = oldItem == newItem
    }
}
