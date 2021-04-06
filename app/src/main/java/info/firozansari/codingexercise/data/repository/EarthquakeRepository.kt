package info.firozansari.codingexercise.data.repository

import info.firozansari.codingexercise.data.local.EarthquakeDao
import info.firozansari.codingexercise.data.remote.ApiResponse
import info.firozansari.codingexercise.data.remote.ApiService
import info.firozansari.codingexercise.data.remote.Earthquake
import info.firozansari.codingexercise.util.toEarthquakeList
import info.firozansari.codingexercise.util.toEntityList
import retrofit2.http.Query

class EarthquakeRepository(
    private val apiService: ApiService,
    private val earthquakeDao: EarthquakeDao
) {

    suspend fun getRemoteEarthquakes(
        @Query("north") north: Float,
        @Query("south") south: Float,
        @Query("east") east: Float,
        @Query("west") west: Float,
        @Query("username") username: String
    ): ApiResponse? {
        return apiService.getEarthquakes(north, south, east, west, username)
    }

    suspend fun getEarthquakesFromDB(): List<Earthquake> {
        return earthquakeDao.getAllEarthquakes().toEarthquakeList()
    }

    suspend fun storeEarthQuakesInDB(items: List<Earthquake>) {
        earthquakeDao.deleteAll()
        earthquakeDao.insertEarthquakes(items.toEntityList())
    }
}