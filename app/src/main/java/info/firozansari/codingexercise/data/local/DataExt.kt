package info.firozansari.codingexercise.util

import info.firozansari.codingexercise.data.local.EarthquakeEntity
import info.firozansari.codingexercise.data.remote.Earthquake

fun List<EarthquakeEntity?>?.toEarthquakeList(): List<Earthquake> {
    val items = mutableListOf<Earthquake>()
    if (this == null) return items.toList()
    val filteredList = this.filterNotNull()
    items.addAll(filteredList.map {
        it.toEarthquake()
    })
    return items.toList()
}

fun EarthquakeEntity.toEarthquake(): Earthquake {
    return Earthquake(
        this.id,
        this.datetime,
        this.depth,
        this.longitude,
        this.latitude,
        this.source,
        this.magnitude
    )
}

fun List<Earthquake?>?.toEntityList(): List<EarthquakeEntity> {
    val entities = mutableListOf<EarthquakeEntity>()
    if (this == null) return entities.toList()
    val filteredList = this.filterNotNull()
    entities.addAll(filteredList.map {
        it.toEntity()
    })
    return entities.toList()
}

fun Earthquake.toEntity(): EarthquakeEntity {
    return EarthquakeEntity(
        this.id,
        this.datetime,
        this.depth,
        this.longitude,
        this.latitude,
        this.source,
        this.magnitude
    )
}
