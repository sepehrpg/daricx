package com.daricx.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DaricxApplication () : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any libraries or components here
    }

}