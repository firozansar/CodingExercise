package info.firozansari.codingexercise.ui.main

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import info.firozansari.codingexercise.BaseMockParser
import info.firozansari.codingexercise.R
import info.firozansari.codingexercise.data.remote.Earthquake
import info.firozansari.codingexercise.BaseMockParser.Companion.EXPECTED_NUM_QUAKES_WHEN_ALL_IDS_VALID
import info.firozansari.codingexercise.testutil.InstrumentedMockParser
import info.firozansari.codingexercise.testutil.RecyclerViewItemCountAssertion
import info.firozansari.codingexercise.ui.main.MockMainViewModel.quakesResult
import info.firozansari.codingexercise.util.getGoogleMapsUrlAt
import io.mockk.every
import org.hamcrest.Matchers
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
        mockQuakeItems = mockParser.getMockQuakeList()
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
        onView(withId(R.id.main_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun main_list_has_expected_number_of_items() {
        // given
        every { mockViewModel.quakesResult } returns quakesResult

        // when
        launchActivityAndMockLiveData()

        // then
        onView(withId(R.id.main_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.main_list)).check(
            RecyclerViewItemCountAssertion(EXPECTED_NUM_QUAKES_WHEN_ALL_IDS_VALID)
        )
    }


    @Test
    fun main_list_item_click_opens_google_maps() {
        // given
        every { mockViewModel.quakesResult } returns quakesResult
        val uri = getGoogleMapsUrlAt(mockQuakeItems[0].latitude!!, mockQuakeItems[0].longitude!!)
        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // when
        launchActivityAndMockLiveData()
        clickRecyclerAt(0)
        mDevice.pressBack() // Pressing back to return from Google Maps

        // To make sure we exited Google Maps
        Thread.sleep(600)

        // then
        Intents.intended(
            Matchers.allOf(
                IntentMatchers.hasPackage("com.google.android.apps.maps"),
                IntentMatchers.hasAction(Intent.ACTION_VIEW),
                IntentMatchers.hasData(uri)
            )
        )
    }

    private fun clickRecyclerAt(position: Int) {
        onView(withId(R.id.main_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ViewActions.click()
            )
        )
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
