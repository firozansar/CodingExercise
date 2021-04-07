package info.firozansari.codingexercise.ui.main

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import info.firozansari.codingexercise.R
import info.firozansari.codingexercise.data.remote.Earthquake
import info.firozansari.codingexercise.databinding.ActivityMainBinding
import info.firozansari.codingexercise.util.getMapIntent
import info.firozansari.codingexercise.util.guardLet
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainViewHolder.ItemClickListener {

    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private var earthquakeList: MutableList<Earthquake> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        mainViewModel.fetchEarthquakes()

        mainViewModel.quakesResult.observe(this, {
            when (it) {
                is EarthquakeResult.Success -> populate(it.items)
                is EarthquakeResult.Error -> populateError(it.error)
            }
        })
    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter(earthquakeList, this)
        mainAdapter.setHasStableIds(true)
        binding.mainList.adapter = mainAdapter
    }

    private fun populate(models: List<Earthquake>) {
        binding.mainList.visibility = View.VISIBLE
        earthquakeList.clear()
        earthquakeList.addAll(models)
        mainAdapter.notifyDataSetChanged()
    }

    private fun populateError(error: String) {
        showToast(error)
    }

    private fun showToast(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun onItemClick(item: Earthquake, position: Int) {
        val (lat, lon) = guardLet(item.latitude, item.longitude) {
            showToast(getString(R.string.invalid_coordinates))
            return
        }
        try {
            startActivity(getMapIntent(lat, lon))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
}