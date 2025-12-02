package com.example.data.di.qualifier

import org.koin.core.annotation.Qualifier

@Qualifier
annotation class Dispatcher(val appDispatcher: AppDispatcher)

enum class AppDispatcher {
    Default,
    IO,
    Main,
}
