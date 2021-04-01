package info.firozansari.codingexercise.di

import info.firozansari.codingexercise.data.repository.EarthquakeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { EarthquakeRepository(get()) }
}