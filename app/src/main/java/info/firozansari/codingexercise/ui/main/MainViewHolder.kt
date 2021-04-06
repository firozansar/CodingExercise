package info.firozansari.codingexercise.ui.main

import androidx.recyclerview.widget.RecyclerView
import info.firozansari.codingexercise.R
import info.firozansari.codingexercise.data.remote.Earthquake
import info.firozansari.codingexercise.databinding.EarthquakeItemBinding
import info.firozansari.codingexercise.util.setTextColorCompat
import info.firozansari.codingexercise.util.setTextOrDefault

class MainViewHolder(
    private val binding: EarthquakeItemBinding,
    private val itemClick: ItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val MAGNITUDE_THRESHOLD = 8.0f
    }

    interface ItemClickListener {
        fun onItemClick(item: Earthquake, position: Int)
    }

    fun bind(
        item: Earthquake,
        position: Int
    ) {
        setupMagnitudeText(item.magnitude)
        binding.itemMagnitude.setTextOrDefault(item.magnitude.toString())

        val dateText = "Date: " + item.datetime
        binding.itemDate.setTextOrDefault(dateText)

        val locationText = "Location: " + "%.3f, %.3f".format(item.latitude, item.longitude)
        binding.itemLocation.setTextOrDefault(locationText)

        itemView.setOnClickListener {
            itemClick.onItemClick(item, position)
        }
    }

    private fun setupMagnitudeText(magnitude: Float?) {
        if (magnitude != null && magnitude >= MAGNITUDE_THRESHOLD) {
            binding.itemMagnitude.setTextColorCompat(R.color.red)
        } else {
            binding.itemMagnitude.setTextColorCompat(R.color.text_warning)
        }
    }

    fun unbind() {
    }
}