package com.example.network.di
import android.content.Context
import com.example.network.utils.ConnectivityManagerNetworkMonitor
import com.example.network.utils.NetworkMonitor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single




/**
 * Koin module for network utilities (e.g., NetworkMonitor).
 */
@Module
@ComponentScan("com.example.network.utils")
class NetworkUtilsModule

