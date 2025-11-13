package com.example.model.sort

enum class DexPairFormat(val value: String) {
    CONTRACT_ADDRESS("contract_address"),
    SYMBOL("symbol");

    // override fun toString(): String = value // in network module used mapper. but with this line no need
}