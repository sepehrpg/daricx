package com.example.network.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.example.network.datasource")
class NetworkDataSourceModule