package com.example.network.interceptor.okhttp

import okhttp3.Call
import okhttp3.EventListener
import okhttp3.Handshake
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy

class OkHttpEventLogger : EventListener() {
    override fun callStart(call: Call) {
        Timber.tag("NET_EVT").d("********** callStart: ${call.request().method} ${call.request().url}")
    }
    override fun dnsStart(call: Call, domainName: String) {
        Timber.tag("NET_EVT").d("dnsStart: $domainName")
    }
    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        Timber.tag("NET_EVT").d("dnsEnd: $domainName -> $inetAddressList")
    }
    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        Timber.tag("NET_EVT").d("connectStart: $inetSocketAddress via $proxy")
    }
    override fun secureConnectStart(call: Call) { Timber.tag("NET_EVT").d("TLS start") }
    override fun secureConnectEnd(call: Call, handshake: Handshake?) { Timber.tag("NET_EVT").d("TLS end") }
    override fun responseHeadersEnd(call: Call, response: Response) {
        Timber.tag("NET_EVT").d("********** request  =${response.request}")
        Timber.tag("NET_EVT").d("********** response  =${response}")
        Timber.tag("NET_EVT").d("********** code =${response.code}")
    }
    override fun callEnd(call: Call) {
        Timber.tag("NET_EVT").d("callEnd")
        Timber.tag("NET_EVT").d("-----------------------------------------------------------------------------------------------------------")
    }
    override fun callFailed(call: Call, ioe: IOException) {
        Timber.tag("NET_EVT").e(ioe, "callFailed")
    }
}