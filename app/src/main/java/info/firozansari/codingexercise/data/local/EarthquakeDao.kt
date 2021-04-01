package info.firozansari.codingexercise.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EarthquakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEarthquakes(items: List<EarthquakeEntity>)

    @Query("SELECT * FROM earthquake_table WHERE id = :eqId")
    suspend fun getEarthquakeById(eqId: String): EarthquakeEntity?

    @Query("SELECT * FROM earthquake_table")
    suspend fun getAllEarthquakes(): List<EarthquakeEntity?>?

    @Query("DELETE FROM earthquake_table")
    suspend fun deleteAll()
}