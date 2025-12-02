package com.example.data.di

import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DispatchersModule {

    @Single
    @Dispatcher(AppDispatcher.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Single
    @Dispatcher(AppDispatcher.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
