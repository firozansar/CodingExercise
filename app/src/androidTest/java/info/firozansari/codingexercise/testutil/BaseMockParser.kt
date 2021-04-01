package info.firozansari.codingexercise.testutil

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import info.firozansari.codingexercise.data.remote.ApiResponse
import info.firozansari.codingexercise.data.remote.Earthquake
import info.firozansari.codingexercise.util.toItems

abstract class BaseMockParser {
    companion object {
        const val EXPECTED_NUM_QUAKES_WHEN_ALL_IDS_VALID = 10
        private const val TEST_QUAKES_FILE_VALID_ITEMS =
            "test_earthquake_data_valid_items.json"
    }

    abstract fun getFileAsString(filePath: String): String

    // get quake core items

    fun getMockQuake(): Earthquake = getMockQuakeFeedAllIdsValid().toItems()[0]

    fun getMockQuakesFromFeedWithAllItemsValid(): ApiResponse =
        ApiResponse(getMockQuakeFeedAllIdsValid().toItems())

    // get json object

    fun getMockQuakeFeedAllIdsValid(): ApiResponse =
        getMockApiResponse(getMockQuakeDataAllIdsValid())

    // get raw string json

    private fun getMockQuakeDataAllIdsValid(): String =
        getFileAsString(TEST_QUAKES_FILE_VALID_ITEMS)


    private fun getMockApiResponse(text: String): ApiResponse {
        return convertToApiResponse(text)
    }

    private inline fun <reified T> convertToApiResponse(jsonString: String?): T {
        val gson = GsonBuilder().setLenient().create()
        val json: JsonObject = gson.fromJson(jsonString, JsonObject::class.java)
        return Gson().fromJson(json, T::class.java)
    }

}