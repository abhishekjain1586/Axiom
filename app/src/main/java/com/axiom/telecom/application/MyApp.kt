package com.axiom.telecom.application

import android.app.Application

class MyApp : Application() {

    companion object {
        lateinit var _INSTANCE: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        _INSTANCE = this
    }
}