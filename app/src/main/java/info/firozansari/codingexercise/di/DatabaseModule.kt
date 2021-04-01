package info.firozansari.codingexercise.di

import androidx.room.Room
import info.firozansari.codingexercise.data.local.EarthquakeDatabase
import info.firozansari.codingexercise.util.Constant.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(androidApplication(), EarthquakeDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
    }

    factory { get<EarthquakeDatabase>().getEarthquakeDao() }
}