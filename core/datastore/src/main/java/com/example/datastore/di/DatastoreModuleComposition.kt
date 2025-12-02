package com.example.datastore.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

/**
 * Koin module that wires the whole datastore layer:
 */
@Module(
    includes = [
        PreferencesDataStoreModule::class,
    ]
)
@ComponentScan("com.example.datastore")
class DatastoreModuleComposition