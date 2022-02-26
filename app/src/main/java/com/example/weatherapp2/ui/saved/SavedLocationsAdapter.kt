package com.example.weatherapp2.ui.saved

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherapp2.R
import com.example.weatherapp2.data.entities.Day
import com.squareup.picasso.Picasso
import kotlin.math.max

class SavedLocationsAdapter(
    private val context: Context,
    private val locations: List<String>,
    private val clickListener: (String) -> Unit
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount() = locations.size

    override fun getItem(p0: Int) = locations[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.saved_item_view, p2, false)

        val savedLocationTv = rowView.findViewById<TextView>(R.id.saved_location_tv)

        val location = getItem(p0).capitalize()

        savedLocationTv.text = location
        savedLocationTv.setOnClickListener {
            clickListener(location)
        }

        return rowView
    }
}