package com.example.weatherapp2.ui.saved

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.weatherapp2.databinding.ActivityMainBinding
import com.example.weatherapp2.databinding.ActivitySavedBinding
import com.example.weatherapp2.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivitySavedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.deleteDBEntry("Queens")
        Log.d("SavedActivity", viewModel.readDB().toString())
    }
}