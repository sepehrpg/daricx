package com.example.network.connections


import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object TestUtils {
    fun jsonBody(json: String): RequestBody =
        json.toRequestBody("application/json; charset=utf-8".toMediaType())
}
