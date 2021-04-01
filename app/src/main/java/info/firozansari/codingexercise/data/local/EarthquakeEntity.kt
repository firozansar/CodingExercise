package info.firozansari.codingexercise.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "earthquake_table")
data class EarthquakeEntity (
    @PrimaryKey
    val id: String,
    val datetime: String?,
    val depth: Float?,
    val longitude: Float?,
    val latitude: Float?,
    val source: String?,
    val magnitude: Float?
)
