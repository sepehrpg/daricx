package com.example.common.di

import com.example.common.config.ConfigModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        ConfigModule::class,
    ]
)
@ComponentScan("com.example.common")
class CommonCompositionModule