package org.irham3.pcparts

import android.app.Application
import org.irham3.core.di.databaseModule
import org.irham3.core.di.networkModule
import org.irham3.core.di.repositoryModule
import org.irham3.pcparts.di.useCaseModule
import org.irham3.pcparts.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}