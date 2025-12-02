package com.example.data.di


import com.example.database.di.DatabaseModuleComposition
import com.example.datastore.di.DatastoreModuleComposition
import com.example.network.di.NetworkModuleComposition
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        DispatchersModule::class,
        CoroutineScopesModule::class,
        // other module
        NetworkModuleComposition::class,
        DatabaseModuleComposition::class,
        DatastoreModuleComposition::class,
    ]
)
@ComponentScan("com.example.data")
class DataModuleComposition
