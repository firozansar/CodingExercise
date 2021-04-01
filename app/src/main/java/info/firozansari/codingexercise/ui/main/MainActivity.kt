package info.firozansari.codingexercise.ui.main

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import info.firozansari.codingexercise.R
import info.firozansari.codingexercise.data.remote.Earthquake
import info.firozansari.codingexercise.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() , MainViewHolder.ItemClickListener {

    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private var earthquakeList: MutableList<Earthquake> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter(earthquakeList, this)
        mainAdapter.setHasStableIds(true)
        binding.mainList.adapter = mainAdapter
    }


    override fun onItemClick(item: Earthquake, position: Int) {
        // TODO
    }
}