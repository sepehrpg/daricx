package com.example.data.nfts.m1

import androidx.paging.testing.asSnapshot
import com.example.data.repository.nfts.NftsRepository
import com.example.data.repository.nfts.NftsRepositoryImpl
import com.example.model.sort.NftsSort
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.model.nfts.NftDetailsDto
import com.example.network.model.nfts.NftsDto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

/**
 * Repository Paging Tests for [NftsRepositoryImpl].
 *
 * Test Goal:
 * - Validate end-to-end paging via Repository (Pager) with a fake remote source.
 *
 * Scenarios Covered:
 * 1) First page loads expected items (robust against prefetchDistance=1 by limiting scroll).
 * 2) Scrolling past end of page1 loads page2; call ordering asserted.
 * 3) Order parameter forwarded to data source along with perPage.
 * 4) pageTransform applied before emission.
 * 5) Stops when next page is empty.
 * 6) Remote error is propagated out of snapshot collection.
 *
 * Prefetch Compatibility:
 * - When testing "first page only", we scroll to index = 0 to **avoid triggering prefetch**.
 *   This keeps the test stable regardless of `prefetchDistance` (even if it is 1).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NftsRepositoryImplTest {

    // ---- Local Fake DataSource (lightweight, focused for repository tests) ----
    private class FakeNftsDataSource(
        private val pages: Map<Int, List<NftsDto>> = emptyMap(),
        private val throwOnPage: Int? = null
    ) : NftsDataSource {

        data class Call(val page: Int, val perPage: Int, val order: NftsSort?)
        private val _calls = mutableListOf<Call>()
        val calls: List<Call> get() = _calls

        override suspend fun getNftsList(
            page: Int,
            perPage: Int,
            order: NftsSort?
        ): List<NftsDto> {
            _calls += Call(page, perPage, order)
            if (throwOnPage == page) throw IOException("boom on page $page")
            return pages[page] ?: emptyList()
        }

        override suspend fun getNftById(id: String): NftDetailsDto {
            TODO("Not yet implemented")
        }
    }

    private fun nftsDto(
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

    @Test
    fun `first page loads and contains expected items (robust to prefetch)`() = runTest {
        val fake = FakeNftsDataSource(
            pages = mapOf(
                1 to listOf(nftsDto("1"), nftsDto("2")),
                2 to listOf(nftsDto("3"))
            )
        )
        val repo: NftsRepository = NftsRepositoryImpl(
            remoteDataSource = fake,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )

        // Scroll only to index 0 -> does not cross prefetch boundary even if prefetchDistance=1
        val snapshot = repo
            .getNftsPaged(pageSize = 2, order = null)
            .asSnapshot { scrollTo(index = 0) }

        assertThat(snapshot.map { it.id })
            .containsAtLeastElementsIn(listOf("1", "2"))
        assertThat(fake.calls.first().page).isEqualTo(1)
    }

    @Test
    fun `scrolling past end of page1 also loads page2`() = runTest {
        val fake = FakeNftsDataSource(
            pages = mapOf(
                1 to listOf(nftsDto("1"), nftsDto("2")),
                2 to listOf(nftsDto("3"))
            )
        )
        val repo = NftsRepositoryImpl(fake, StandardTestDispatcher(testScheduler))

        val snapshot = repo
            .getNftsPaged(pageSize = 2, order = null)
            .asSnapshot { scrollTo(index = 2) } // crosses boundary -> loads page2

        assertThat(snapshot.map { it.id })
            .containsAtLeastElementsIn(listOf("1", "2", "3")).inOrder()
        assertThat(fake.calls.map { it.page }).containsExactly(1, 2).inOrder()
    }

    @Test
    fun `forwards order param to data source`() = runTest {
        val fake = FakeNftsDataSource(
            pages = mapOf(1 to listOf(nftsDto("x")))
        )
        val repo = NftsRepositoryImpl(fake, StandardTestDispatcher(testScheduler))

        val snapshot = repo.getNftsPaged(
            pageSize = 50,
            order = NftsSort.MarketCapUsdDesc
        ).asSnapshot { scrollTo(index = 0) }

        assertThat(snapshot.map { it.id }).contains("x")
        assertThat(fake.calls.first().order).isEqualTo(NftsSort.MarketCapUsdDesc)
        assertThat(fake.calls.first().perPage).isEqualTo(50)
    }

    @Test
    fun `applies pageTransform before emission`() = runTest {
        val fake = FakeNftsDataSource(
            pages = mapOf(1 to listOf(nftsDto("1"), nftsDto("2"), nftsDto("3")))
        )
        val repo = NftsRepositoryImpl(fake, StandardTestDispatcher(testScheduler))

        val snapshot = repo.getNftsPaged(
            pageSize = 3,
            order = null,
            pageTransform = { it.asReversed() }
        ).asSnapshot { scrollTo(index = 2) }

        assertThat(snapshot.take(3).map { it.id }).containsExactly("3", "2", "1").inOrder()
    }

    @Test
    fun `stops when next page is empty`() = runTest {
        val fake = FakeNftsDataSource(
            pages = mapOf(
                1 to listOf(nftsDto("a"), nftsDto("b")),
                2 to emptyList()
            )
        )
        val repo = NftsRepositoryImpl(fake, StandardTestDispatcher(testScheduler))

        val snapshot = repo
            .getNftsPaged(pageSize = 2, order = null)
            .asSnapshot { scrollTo(index = 10) }

        assertThat(snapshot.map { it.id }).containsExactly("a", "b").inOrder()
        assertThat(fake.calls.map { it.page }).containsExactly(1, 2).inOrder()
    }

    @Test
    fun `propagates remote error as exception during snapshot run`() = runTest {
        val fake = FakeNftsDataSource(throwOnPage = 1)
        val repo = NftsRepositoryImpl(fake, StandardTestDispatcher(testScheduler))

        var thrown: Throwable? = null
        try {
            repo.getNftsPaged(pageSize = 20, order = null).asSnapshot {
                scrollTo(index = 0)
            }
        } catch (t: Throwable) {
            thrown = t
        }
        assertThat(thrown).isInstanceOf(IOException::class.java)
    }
}
