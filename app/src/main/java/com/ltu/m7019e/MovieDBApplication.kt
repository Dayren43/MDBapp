package com.ltu.m7019e

import android.app.Application
import com.ltu.m7019e.database.AppContainer
import com.ltu.m7019e.database.DefaultAppContainer

class MovieDBApplication : Application()  {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}