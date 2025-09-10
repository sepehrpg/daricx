package com.example.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import timber.log.Timber
import java.nio.charset.Charset

class BodyLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 👉 Log the request method and full URL
        Timber.tag("BodyLoggingInterceptor").d("Request method and full URL: ${request.method} ${request.url}")
        Timber.tag("BodyLoggingInterceptor").d("------------------")

        request.body?.let { body ->
            val buffer = Buffer()
            body.writeTo(buffer)
            val charset = body.contentType()?.charset(Charset.forName("UTF-8")) ?: Charsets.UTF_8
            // 👉 Log the request body (JSON or form-data)
            Timber.tag("BodyLoggingInterceptor").d(" Log the request body (JSON or form-data):  ${buffer.readString(charset)}")
            Timber.tag("BodyLoggingInterceptor").d("------------------")
        }

        val response = chain.proceed(request)

        val responseBody = response.body
        val contentType = responseBody?.contentType()

        if (responseBody != null) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer

            val charset = contentType?.charset(Charset.forName("UTF-8")) ?: Charsets.UTF_8
            val bodyString = buffer.clone().readString(charset)

            // 👉 Log the response code and URL
            Timber.tag("BodyLoggingInterceptor").d("Code=${response.code} url=${response.request.url}")
            Timber.tag("BodyLoggingInterceptor").d("------------------")

            // 👉 Log the response body (raw JSON string)
            Timber.tag("BodyLoggingInterceptor").d("Response body: $bodyString")

            // 👉 Log a separator for better readability
            Timber.tag("BodyLoggingInterceptor").d("------------------------------------------------------------------------------------------------")

            return response.newBuilder()
                .body(ResponseBody.create(contentType, bodyString))
                .build()
        }

        return response
    }
}
