package info.firozansari.codingexercise.data.remote

fun ApiResponse?.toItems(): List<Earthquake> {
    val items = mutableListOf<Earthquake>()
    if (this == null || this.earthquakes == null) return items.toList()
    val filteredList = this.earthquakes!!.filter { !it.id.isNullOrBlank() }
    items.addAll(filteredList.map {
        it.toItem(it.id)
    })
    return items.toList()
}

private fun Earthquake.toItem(id: String): Earthquake {
    return Earthquake(
        id,
        this.datetime,
        this.depth,
        this.longitude,
        this.latitude,
        this.source,
        this.magnitude
    )
}
