package info.firozansari.codingexercise.data.repository

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import info.firozansari.codingexercise.data.local.EarthquakeDao
import info.firozansari.codingexercise.data.local.EarthquakeEntity
import info.firozansari.codingexercise.data.remote.ApiService
import info.firozansari.codingexercise.data.remote.Earthquake
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

    @Mock
    private lateinit var mockDao: EarthquakeDao

    private var subject: EarthquakeRepository


    init {
        initialise()
        subject = EarthquakeRepository(mockApiService, mockDao)
    }


    @Test
    fun `get local earthquake list`() {
        // when
        runBlocking {
            // given
            val mockEntityList = getMockDataFromDBWithAllItemsValid()
            Mockito.`when`(mockDao.getAllEarthquakes()).thenReturn(mockEntityList)

            // when
            val items = subject.getEarthquakesFromDB()

            // then
            verify(mockDao, times(1)).getAllEarthquakes()
            verifyLocalList(items, mockEntityList)
        }
    }

    @Test
    fun `get remote earthquake list `() {
        // when
        runBlocking {
            // given
            val mockFeed = getMockQuakeFeedAllIdsValid()
            Mockito.`when`(mockApiService.getEarthquakes(
                mockNorthBound,
                mockSouthBound,
                mockEastBound,
                mockWestBound,
                mockUsername
            )).thenReturn(mockFeed)

            // when
            val remoteItems = subject.getRemoteEarthquakes(
                mockNorthBound,
                mockSouthBound,
                mockEastBound,
                mockWestBound,
                mockUsername
            )

            // then
            verify(mockApiService, times(1)).getEarthquakes(
                mockNorthBound,
                mockSouthBound,
                mockEastBound,
                mockWestBound,
                mockUsername
            )

            assertEquals(remoteItems, mockFeed)
        }
    }

    private fun verifyLocalList(
        actualItems: List<Earthquake>,
        entityList: List<EarthquakeEntity>
    ) {
        if (entityList.isNullOrEmpty()) {
            return
        }

        entityList.forEach lit@{ entity ->
            if (entity.id.isNullOrBlank()) return@lit
            actualItems.forEach { actualItem ->
                if (entity.id == actualItem.id) {
                    verifyEachLocalItem(actualItem, entity)
                    return@lit
                }
            }
        }
    }

    private fun verifyEachLocalItem(
        actual: Earthquake,
        expected: EarthquakeEntity
    ) {
        assertEquals(expected.id, actual.id)
        assertEquals(expected.datetime, actual.datetime)
        assertEquals(expected.depth, actual.depth)
        assertEquals(expected.longitude, actual.longitude)
        assertEquals(expected.latitude, actual.latitude)
        assertEquals(expected.source, actual.source)
        assertEquals(expected.magnitude, actual.magnitude)
    }

}