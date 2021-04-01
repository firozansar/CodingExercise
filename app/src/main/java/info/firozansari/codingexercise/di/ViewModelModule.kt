package info.firozansari.codingexercise.di

import info.firozansari.codingexercise.ui.main.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single {
        MainViewModel(get(), get())
    }
}