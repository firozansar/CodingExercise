package info.firozansari.codingexercise.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.firozansari.codingexercise.data.repository.EarthquakeRepository
import info.firozansari.codingexercise.util.Constant.EAST
import info.firozansari.codingexercise.util.Constant.NORTH
import info.firozansari.codingexercise.util.Constant.SOUTH
import info.firozansari.codingexercise.util.Constant.USERNAME
import info.firozansari.codingexercise.util.Constant.WEST
import info.firozansari.codingexercise.util.EarthquakeResult
import kotlinx.coroutines.launch

class MainViewModel (
        private val earthquakeRepository: EarthquakeRepository,
) : ViewModel() {

        companion object {
                const val ERROR_MESSAGE = "Something went wrong"
        }

        private val mQuakesResult = MutableLiveData<EarthquakeResult>()
        val quakesResult: LiveData<EarthquakeResult>
                get() = mQuakesResult

        fun fetchEarthquakes() {
          fetchRemoteEarthquakes()
        }

        fun fetchRemoteEarthquakes() {
                viewModelScope.launch {
                        try {
                                val remoteList = earthquakeRepository.getRemoteEarthquakes(NORTH, SOUTH, EAST, WEST, USERNAME)
                                if(remoteList == null) {
                                        mQuakesResult.value = EarthquakeResult.Error(ERROR_MESSAGE)
                                } else {
                                        remoteList.earthquakes.let{  eqList ->
                                                eqList?.let {
                                                        mQuakesResult.value = EarthquakeResult.Success(it)
                                                }
                                        }
                                }
                        } catch (e: Exception) {
                                mQuakesResult.value = EarthquakeResult.Error(getErrorMessage(e))
                        }
                }
        }

        fun fetchLocalEarthquakes() {

        }

        private fun getErrorMessage(e: Exception): String {
                return e.message ?: ERROR_MESSAGE
        }


}
