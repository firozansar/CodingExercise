package info.firozansari.codingexercise.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("earthquakesJSON")
    suspend fun getEarthquakes(
        @Query("north") north: Float,
        @Query("south") south: Float,
        @Query("east") east: Float,
        @Query("west") west: Float,
        @Query("username") username: String
    ): ApiResponse?
}