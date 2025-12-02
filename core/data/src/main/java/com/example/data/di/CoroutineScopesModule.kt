
package com.example.data.di


import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Module
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single


@Qualifier
annotation class ApplicationScope

@Module
class CoroutineScopesModule {

    @Single
    @ApplicationScope
    fun provideApplicationScope(
        @Dispatcher(AppDispatcher.Default) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}