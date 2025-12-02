package com.example.data.di


import com.example.common.di.CommonCompositionModule
import com.example.database.di.DatabaseCompositionModule
import com.example.datastore.di.DatastoreCompositionModule
import com.example.network.di.CompositionNetworkModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@Module(
    includes = [
        DispatchersModule::class,
        CoroutineScopesModule::class,
        // other module
        CompositionNetworkModule::class,
        DatabaseCompositionModule::class,
        DatastoreCompositionModule::class,
        CommonCompositionModule::class,
    ]
)
@ComponentScan("com.example.data")
class DataCompositionModule
