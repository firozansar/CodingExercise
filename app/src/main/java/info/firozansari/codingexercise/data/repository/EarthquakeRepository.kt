package info.firozansari.codingexercise.data.repository

import info.firozansari.codingexercise.data.remote.ApiResponse
import info.firozansari.codingexercise.data.remote.ApiService
import retrofit2.http.Query

class EarthquakeRepository (
    private val apiService: ApiService
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

}