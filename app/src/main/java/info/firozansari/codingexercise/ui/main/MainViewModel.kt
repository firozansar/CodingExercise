package info.firozansari.codingexercise.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.firozansari.codingexercise.data.repository.EarthquakeRepository
import info.firozansari.codingexercise.data.remote.NetworkManager
import kotlinx.coroutines.launch

class MainViewModel(
        private val earthquakeRepository: EarthquakeRepository,
        private val networkManager: NetworkManager
) : ViewModel() {

    companion object {
        const val ERROR_MESSAGE = "Something went wrong"
        const val NORTH = 44.1F
        const val SOUTH = -9.9F
        const val EAST = -22.4F
        const val WEST = 55.2F
        const val USERNAME = "mkoppelman"
    }

    private val mQuakesResult = MutableLiveData<EarthquakeResult>()
    val quakesResult: LiveData<EarthquakeResult>
        get() = mQuakesResult

    fun fetchEarthquakes() {
        if (networkManager.isOnline()) fetchRemoteEarthquakes()
        else fetchLocalEarthquakes()
    }

    fun fetchRemoteEarthquakes() {
        viewModelScope.launch {
            try {
                val remoteList =
                    earthquakeRepository.getRemoteEarthquakes(NORTH, SOUTH, EAST, WEST, USERNAME)
                if (remoteList == null) {
                    mQuakesResult.value = EarthquakeResult.Error(ERROR_MESSAGE)
                } else {
                    remoteList.earthquakes.let { eqList ->
                        eqList?.let {
                            mQuakesResult.value = EarthquakeResult.Success(it)
                            earthquakeRepository.storeEarthQuakesInDB(it)
                        }
                    }
                }
            } catch (e: Exception) {
                mQuakesResult.value = EarthquakeResult.Error(getErrorMessage(e))
            }
        }
    }

    fun fetchLocalEarthquakes() {
        viewModelScope.launch {
            try {
                val localList = earthquakeRepository.getEarthquakesFromDB()
                mQuakesResult.value = EarthquakeResult.Success(localList)
            } catch (e: Exception) {
                EarthquakeResult.Error(getErrorMessage(e))
            }
        }
    }

    private fun getErrorMessage(e: Exception): String {
        return e.message ?: ERROR_MESSAGE
    }
}