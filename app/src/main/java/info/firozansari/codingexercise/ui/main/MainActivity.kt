package info.firozansari.codingexercise.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import info.firozansari.codingexercise.R
import info.firozansari.codingexercise.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    //private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}