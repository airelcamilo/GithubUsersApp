package com.airelcamilo.githubusersapp

import android.app.Application
import com.airelcamilo.core.di.databaseModule
import com.airelcamilo.core.di.networkModule
import com.airelcamilo.core.di.repositoryModule
import com.airelcamilo.githubusersapp.di.useCaseModule
import com.airelcamilo.githubusersapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}