package com.omens.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.omens.weather.model.WeatherCityResponse
import com.omens.weather.utils.NavigationEventHandler
import com.squareup.picasso.Picasso


class CustomAdapter(private var mList: List<WeatherCityResponse>, handler: NavigationEventHandler) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private var selectCheck: ArrayList<Int?>? = ArrayList()
    private lateinit var view: View

    private val list = handler

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        for (i in mList.indices) {
            selectCheck!!.add(0)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.cityPlusCountry.text =  view.resources.getString(R.string.city_and_country_code,itemsViewModel.name,itemsViewModel.sys!!.country)
        holder.tempText.text =  view.resources.getString(R.string.temp_short,list.kelvinToCelsius(itemsViewModel.main!!.temp))

        for(data in itemsViewModel.weather!!.indices){
            val tmpData = itemsViewModel.weather[data]
            val iconUrl = "http://openweathermap.org/img/w/" + tmpData!!.icon + ".png"
            Picasso.get().load(iconUrl).resize(150, 150 ).centerCrop().into(holder.imageIcon)
        }

        holder.itemLayout.setOnClickListener {
            list.setObject(itemsViewModel)
            list.navigateToFragment(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cityPlusCountry: TextView = itemView.findViewById(R.id.item_city)
        val tempText: TextView = itemView.findViewById(R.id.item_temp)
        val imageIcon: ImageView = itemView.findViewById(R.id.weather_image_view)

        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.item_search_result)
    }

    fun reloadList(items: List<WeatherCityResponse>) {
        mList = items
        notifyDataSetChanged()
    }
}
