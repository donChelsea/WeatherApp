package com.example.weatherapp2.ui.forecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherapp2.R
import com.example.weatherapp2.data.entities.Day

class ForecastDatesAdapter(
    private val context: Context,
    private val forecast: List<Day>,
    private val clickListener: (Day) -> Unit
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount() = forecast.size

    override fun getItem(p0: Int) = forecast[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.forecast_item_view, p2, false)

        val currentTempTv = rowView.findViewById(R.id.current_temp_tv) as TextView
        val timeTv = rowView.findViewById(R.id.time_tv) as TextView
        val weatherTv = rowView.findViewById(R.id.weather_tv) as TextView
        val maxTempTextTv = rowView.findViewById(R.id.max_temp_text_tv) as TextView
        val minTempTextTv = rowView.findViewById(R.id.min_temp_text_tv) as TextView
        val weatherIcon = rowView.findViewById(R.id.weather_icon) as ImageView

        val day = getItem(p0)

        currentTempTv.text = day.temp.temp.toInt().toString()
        timeTv.text = day.dateTimeString.split(" ")[1]
        weatherTv.text = day.weather[0].description
        maxTempTextTv.text = day.temp.max.toInt().toString()
        minTempTextTv.text = day.temp.min.toInt().toString()

        rowView.setOnClickListener {
            clickListener(day)
        }

//        Picasso.get().load()

        return rowView
    }
}