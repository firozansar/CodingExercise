package info.firozansari.codingexercise

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import info.firozansari.codingexercise.di.networkModule
import info.firozansari.codingexercise.di.repositoryModule
import info.firozansari.codingexercise.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

open class InstrumentedRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return Instrumentation.newApplication(AppTest::class.java, context)
    }
}

class AppTest : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}