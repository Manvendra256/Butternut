package com.example.butternut

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ButternutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}