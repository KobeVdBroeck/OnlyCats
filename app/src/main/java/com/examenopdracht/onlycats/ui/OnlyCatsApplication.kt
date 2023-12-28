package com.examenopdracht.onlycats.ui

import android.app.Application
import com.examenopdracht.onlycats.data.AppContainer
import com.examenopdracht.onlycats.data.DefaultAppContainer

class OnlyCatsApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}