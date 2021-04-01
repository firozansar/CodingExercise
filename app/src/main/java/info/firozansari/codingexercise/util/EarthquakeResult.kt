package info.firozansari.codingexercise.util

import info.firozansari.codingexercise.data.remote.Earthquake

sealed class EarthquakeResult {
    data class Success(val items: List<Earthquake>): EarthquakeResult()
    data class Error(val error: String): EarthquakeResult()
}