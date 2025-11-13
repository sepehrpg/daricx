package com.example.data.nfts.m1

import androidx.annotation.VisibleForTesting
import com.example.model.sort.NftsSort
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.model.nfts.NftDetailsDto
import com.example.network.model.nfts.NftsDto
import com.example.network.model.nfts.NftsListDto
import java.io.IOException
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

/**
 * Test Double: Controllable fake for [NftsDataSource].
 *
 * Test Goal:
 * - Provide deterministic, thread-safe responses for paging tests and repository tests.
 *
 * Scenarios Covered:
 * 1) Return a preconfigured list of DTOs per page (via [pages]).
 * 2) Record every call (page, perPage, order) for assertion.
 * 3) Optionally throw on a specific page (via [throwOnPage]) to verify error propagation.
 *
 * Usage Tips:
 * - Prefer small, explicit page maps (e.g., pages = mapOf(1 to listOf(dto1, dto2))).
 * - Use [calls] to assert request parameters (page sequencing, perPage forwarding, order forwarding).
 * - To test "first page only" with prefetchDistance=1, limit scrolling to index 0 in snapshot tests.
 */
class FakeNftsDataSource(
    private val pages: Map<Int, NftsListDto>,
    private val throwOnPage: Int? = null
) : NftsDataSource {

    data class Call(val page: Int, val perPage: Int, val order: NftsSort?)

    private val _calls = CopyOnWriteArrayList<Call>()
    val calls: List<Call> get() = _calls.toList()

    val totalCalls = AtomicInteger(0)

    override suspend fun getNftsList(
        page: Int,
        perPage: Int,
        order: NftsSort?
    ): NftsListDto {
        totalCalls.incrementAndGet()
        _calls += Call(page, perPage, order)
        if (throwOnPage == page) throw IOException("boom on page $page")
        return pages[page] ?: emptyList()
    }

    override suspend fun getNftById(id: String): NftDetailsDto {
        TODO("Not yet implemented")
    }

    @VisibleForTesting
    fun reset() {
        _calls.clear()
        totalCalls.set(0)
    }
}

/** Small, handy builder for DTO samples used across tests. */
fun nftsDto(
    id: String,
    name: String = "NFT $id",
    platform: String = "ethereum",
    contract: String = "0x$id",
    symbol: String = "SYM$id"
) = NftsDto(
    assetPlatformId = platform,
    contractAddress = contract,
    id = id,
    name = name,
    symbol = symbol
)
