package info.firozansari.codingexercise.ui

import android.app.Application
import info.firozansari.codingexercise.di.databaseModule
import info.firozansari.codingexercise.di.networkModule
import info.firozansari.codingexercise.di.repositoryModule
import info.firozansari.codingexercise.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger() // Koin Logger
            androidContext(this@App)
            modules(listOf(networkModule, databaseModule, repositoryModule, viewModelModule))
        }

    }
}