package info.firozansari.codingexercise.data.repository

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import info.firozansari.codingexercise.data.remote.ApiService
import info.firozansari.codingexercise.testutil.TestData
import info.firozansari.codingexercise.testutil.UnitTestSetup
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(JUnit4::class)
class EarthquakeRepositoryTest : UnitTestSetup() {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var subject: EarthquakeRepository

    private val testData: TestData = TestData()

    init {
        initialise()
    }

    override fun initialiseClassUnderTest() {
        subject = EarthquakeRepository(mockApiService)
    }


    @Test
    fun `get remote earthquake list `() {
        // when
        runBlocking {
            // given
            val mockFeed = testData.getMockQuakeFeedAllIdsValid()
            Mockito.`when`(mockApiService.getEarthquakes(
                testData.mockNorthBound,
                testData.mockSouthBound,
                testData.mockEastBound,
                testData.mockWestBound,
                testData.mockUsername
            )).thenReturn(mockFeed)

            // when
            val remoteItems = subject.getRemoteEarthquakes(
                testData.mockNorthBound,
                testData.mockSouthBound,
                testData.mockEastBound,
                testData.mockWestBound,
                testData.mockUsername
            )

            // then
            verify(mockApiService, times(1)).getEarthquakes(
                testData.mockNorthBound,
                testData.mockSouthBound,
                testData.mockEastBound,
                testData.mockWestBound,
                testData.mockUsername
            )

            assertEquals(remoteItems, mockFeed)
        }
    }

}