package info.firozansari.codingexercise.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.firozansari.codingexercise.util.EarthquakeResult
import io.mockk.mockk

object MockMainViewModel {

    private var mockMainViewModel: MainViewModel = mockk(relaxed = true)

    var mQuakesResult = MutableLiveData<EarthquakeResult>()
    val quakesResult: LiveData<EarthquakeResult>
        get() = mQuakesResult

    fun getMockMainViewModel(): MainViewModel {
        return mockMainViewModel
    }

}