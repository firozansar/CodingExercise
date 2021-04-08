package info.firozansari.codingexercise

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import info.firozansari.codingexercise.data.local.EarthquakeEntity
import info.firozansari.codingexercise.data.remote.ApiResponse
import info.firozansari.codingexercise.data.remote.Earthquake
import info.firozansari.codingexercise.util.toEntityList
import info.firozansari.codingexercise.util.toItems

 open class BaseMockParser {
    companion object {
        const val EXPECTED_NUM_QUAKES_WHEN_ALL_IDS_VALID = 10
        private const val TEST_QUAKES_FILE_VALID_ITEMS =
            "test_earthquake_data_valid_items.json"
    }

    fun getFileAsString(filePath: String): String {
        return ClassLoader.getSystemResource(filePath).readText()
    }

    fun getMockQuake(): Earthquake = getMockQuakeList().first()

    fun getMockQuakeList(): List<Earthquake> = getMockQuakeFeedAllIdsValid().toItems()

    fun getMockDataFromDBWithAllItemsValid(): List<EarthquakeEntity> =
        getMockQuakeFeedAllIdsValid().toItems().toEntityList()

    fun getMockQuakeFeedAllIdsValid(): ApiResponse =
        getMockApiResponse(getMockQuakeDataAllIdsValid())

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