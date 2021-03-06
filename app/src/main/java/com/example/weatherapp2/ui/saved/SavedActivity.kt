package com.example.weatherapp2.ui.saved

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.weatherapp2.databinding.ActivitySavedBinding
import com.example.weatherapp2.ui.MainActivity
import com.example.weatherapp2.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivitySavedBinding
    var adapter: SavedLocationsAdapter? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locations = viewModel.readDB()

        binding.apply {
            adapter = locations.let { SavedLocationsAdapter(this@SavedActivity, it, {location -> onLocationClick(location)}, {location -> onDelete(location)})  }
            savedListView.adapter = adapter
        }
    }

    private fun onLocationClick(location: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(ARG_SAVED_LOCATION, location)
        startActivity(intent)
    }

    private fun onDelete(location: String) {
        viewModel.deleteDBEntry(location)
        adapter?.notifyDataSetChanged()
    }

    companion object {
        const val ARG_SAVED_LOCATION = "saved location"
    }

}