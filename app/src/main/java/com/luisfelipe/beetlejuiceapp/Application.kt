package com.luisfelipe.beetlejuiceapp

import android.app.Application
import com.luisfelipe.movie.di.movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

    }

    private fun initKoin() {
        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    movieModule
                )
            )
        }
    }

}