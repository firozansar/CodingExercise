package info.firozansari.codingexercise.ui.main

import androidx.lifecycle.ViewModel
import info.firozansari.codingexercise.data.repository.EarthquakeRepository

class MainViewModel (
        private val earthquakeRepository: EarthquakeRepository,
) : ViewModel() {


        fun fetchRemoteEarthquakes() {

        }

        fun fetchLocalEarthquakes() {

        }

}
