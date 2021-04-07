package info.firozansari.codingexercise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EarthquakeEntity::class], version = 1, exportSchema = false)
abstract class EarthquakeDatabase : RoomDatabase() {

    abstract fun getEarthquakeDao(): EarthquakeDao
}
