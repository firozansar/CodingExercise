package info.firozansari.codingexercise.testutil

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import info.firozansari.codingexercise.data.local.EarthquakeEntity
import info.firozansari.codingexercise.data.remote.ApiResponse
import info.firozansari.codingexercise.util.toEntityList
import info.firozansari.codingexercise.util.toItems

class TestData {
    val mockNorthBound = 44.1f
    val mockSouthBound = -9.9f
    val mockEastBound = -22.4f
    val mockWestBound = 55.2f
    val mockUsername = "testuser"

    fun getFileAsJsonString(filePath: String): String =
        ClassLoader.getSystemResource(filePath).readText()

    companion object {
        private const val TEST_QUAKES_FILE_VALID_ITEMS =
            "test_earthquake_data_valid_items.json"
    }

    // get quake local items (database entities)

    fun getMockDataFromDBWithAllItemsValid(): List<EarthquakeEntity> =
        getMockQuakeFeedAllIdsValid().toItems().toEntityList()

    fun getMockQuakeFeedAllIdsValid(): ApiResponse =
        getMockApiResponse(getMockQuakeDataAllIdsValid())

    private fun getMockQuakeDataAllIdsValid(): String =
        getFileAsJsonString(TEST_QUAKES_FILE_VALID_ITEMS)

    private fun getMockApiResponse(text: String): ApiResponse {
        return convertToApiResponse(text)
    }

    private inline fun <reified T> convertToApiResponse(jsonString: String?): T {
        val gson = GsonBuilder().setLenient().create()
        val json: JsonObject = gson.fromJson(jsonString, JsonObject::class.java)
        return Gson().fromJson(json, T::class.java)
    }

}