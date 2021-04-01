package info.firozansari.codingexercise.ui.main

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import info.firozansari.codingexercise.R
import info.firozansari.codingexercise.data.remote.Earthquake
import info.firozansari.codingexercise.testutil.InstrumentedMockParser
import info.firozansari.codingexercise.ui.main.MockMainViewModel.quakesResult
import info.firozansari.codingexercise.util.EarthquakeResult
import io.mockk.every
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest : KoinTest {

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(
            MainActivity::class.java,
            false, false
        )

    private val mockParser = InstrumentedMockParser()
    private lateinit var mockViewModel: MainViewModel
    private lateinit var mockQuakeItems: List<Earthquake>
    private lateinit var quakesSuccess: EarthquakeResult.Success


    @Before
    fun setUp() {
        Intents.init()
        mockQuakeItems = mockParser.getMockQuakesFromFeedWithAllItemsValid().earthquakes!!
        quakesSuccess = EarthquakeResult.Success(mockQuakeItems)
        mockViewModel = MockMainViewModel.getMockMainViewModel()
        every { mockViewModel.fetchEarthquakes() } returns Unit

    }

    @Test
    fun main_list_is_displayed() {
        // given
        every { mockViewModel.quakesResult } returns quakesResult

        // when
        launchActivityAndMockLiveData()

        // then
        Espresso.onView(withId(R.id.main_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }


    private fun launchActivityAndMockLiveData() {
        testRule.launchActivity(null)
        testRule.activity.runOnUiThread {
            MockMainViewModel.mQuakesResult.value = quakesSuccess
        }
    }


    @After
    fun tearDown() {
        Intents.release()
    }

}