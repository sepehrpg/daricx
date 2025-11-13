package com.example.data.nfts



import com.example.common.result.AppResult
import com.example.data.repository.nfts.NftsRepositoryImpl
import com.example.model.nfts.NftDetails
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.model.mappers.nfts.toDomain
import com.example.network.model.nfts.NftDetailsDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NftsRepositoryImplGetNftByIdTest {

    private val dispatcher = StandardTestDispatcher()

    @Test
    fun `getNftById emits Loading then Success with mapped domain`() = runTest(dispatcher) {
        // Arrange
        val remote: NftsDataSource = mockk()
        val repo = NftsRepositoryImpl(remote, dispatcher)
        val id = "bored-ape-yacht-club"

        val dto: NftDetailsDto = mockk(relaxed = true)
        val expected: NftDetails = dto.toDomain()

        coEvery { remote.getNftById(id) } returns dto

        // Act
        val emissions = mutableListOf<AppResult<NftDetails>>()
        repo.getNftById(id).toList(emissions)

        // Assert
        assertEquals(2, emissions.size)
        assertIs<AppResult.Loading>(emissions[0])
        val success = emissions[1]
        assertIs<AppResult.Success<NftDetails>>(success)
        assertEquals(expected, success.data)

        coVerify(exactly = 1) { remote.getNftById(id) }
    }

    @Test
    fun `getNftById emits Loading then Error when remote throws`() = runTest(dispatcher) {
        // Arrange
        val remote: NftsDataSource = mockk()
        val repo = NftsRepositoryImpl(remote, dispatcher)
        val id = "unknown-nft"

        coEvery { remote.getNftById(id) } throws IllegalStateException("Network failure")

        // Act
        val emissions = mutableListOf<AppResult<NftDetails>>()
        repo.getNftById(id).toList(emissions)

        // Assert
        assertEquals(2, emissions.size)
        assertIs<AppResult.Loading>(emissions[0])
        assertIs<AppResult.Error>(emissions[1])
        coVerify(exactly = 1) { remote.getNftById(id) }
    }
}
