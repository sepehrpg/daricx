package com.example.network.options
import com.example.model.sort.DexPairFormat
import com.example.model.sort.NftsSort


// if use this override fun toString(): String = value in enum class don't need to this mapper
fun DexPairFormat.toDomain(): String = when (this) {
    DexPairFormat.CONTRACT_ADDRESS      -> "contract_address"
    DexPairFormat. SYMBOL    -> "symbol"
}