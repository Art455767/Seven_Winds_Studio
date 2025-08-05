package com.example.sevenwindsstudio

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoffeeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("177211c4-02e4-4d4a-b2b9-f11e5d29c6f4")
        MapKitFactory.initialize(this)
    }
}


