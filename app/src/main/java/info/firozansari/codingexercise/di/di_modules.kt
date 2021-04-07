package info.firozansari.codingexercise.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import info.firozansari.codingexercise.BuildConfig
import info.firozansari.codingexercise.data.local.EarthquakeDatabase
import info.firozansari.codingexercise.data.remote.ApiService
import info.firozansari.codingexercise.data.remote.NetworkManager
import info.firozansari.codingexercise.data.repository.EarthquakeRepository
import info.firozansari.codingexercise.ui.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {

    single {
        Room.databaseBuilder(androidApplication(), EarthquakeDatabase::class.java, "earthquake.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { get<EarthquakeDatabase>().getEarthquakeDao() }
}

val networkModule = module {

    single { GsonBuilder().create() }

    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        OkHttpClient.Builder().apply {
            connectTimeout(10L, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
    }

    single {
        NetworkManager(get())
    }

    factory {
        get<Retrofit>().create(ApiService::class.java)
    }

}

val repositoryModule = module {
    single { EarthquakeRepository(get(), get()) }
}

val viewModelModule = module {
    single {
        MainViewModel(get(), get())
    }
}