package com.example.weatherapp2.ui.forecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp2.databinding.FragmentForecastBinding
import com.example.weatherapp2.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(layoutInflater, container, false)
        return binding.apply {
            viewModel.position = arguments?.get(ARG_POSITION).toString().toInt()
            viewModel.currentDayInView = arguments?.get(ARG_DAY).toString()
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val hourly = viewModel.weekMap[viewModel.currentDayInView]?.let {
                it.toList()
            }

            val adapter = hourly?.let { ForecastDatesAdapter(requireContext(), it) }
            forecastListView.adapter = adapter

            dateTv.text = viewModel.currentDayInView
        }
    }

    companion object {
        private const val ARG_POSITION = "position"
        private const val ARG_DAY = "day"

        fun getInstance(position: Int, day: String): Fragment {
            val forecastFragment = ForecastFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            bundle.putString(ARG_DAY, day)
            forecastFragment.arguments = bundle
            return forecastFragment
        }
    }

}