package info.firozansari.codingexercise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import info.firozansari.codingexercise.util.Constant.DATABASE_VERSION
import info.firozansari.codingexercise.util.Constant.EXPORT_SCHEMA


@Database(entities = [EarthquakeEntity::class], version = DATABASE_VERSION, exportSchema = EXPORT_SCHEMA)
abstract class EarthquakeDatabase : RoomDatabase() {

    abstract fun getEarthquakeDao(): EarthquakeDao

}
