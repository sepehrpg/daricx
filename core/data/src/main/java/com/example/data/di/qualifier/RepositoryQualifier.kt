package com.example.data.di.qualifier

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OfflineRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OnlineRepository