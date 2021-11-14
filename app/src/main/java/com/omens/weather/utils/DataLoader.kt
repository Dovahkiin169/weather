package com.omens.weather.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.omens.weather.CustomAdapter
import com.omens.weather.model.ListWeatherCityResponse
import com.omens.weather.model.WeatherCityResponse
import com.omens.weather.service.Service
import com.omens.weather.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val service = ServiceBuilder.buildService(Service::class.java)
var gson = Gson()

fun getListLikeString(city: String,context: Context, listener: NavigationEventHandler, adapter: CustomAdapter, callback: (item: ListWeatherCityResponse?) -> Unit) {
    listener.showHideProgressbar(true)
    val requestCall = service.getListOfCities("json","like",city)
    Log.e("requestCall",requestCall.toString())

    if(isNetworkAvailable(context)) {
        requestCall.enqueue(object : Callback<ListWeatherCityResponse> {
            override fun onResponse(call: Call<ListWeatherCityResponse>, response: Response<ListWeatherCityResponse>) {
                if (response.isSuccessful) {
                    val eventsList = response.body()
                    eventsList.let { callback.invoke(it) }
                    adapter.reloadList(eventsList!!.list!!)
                    if(eventsList.list!!.isEmpty())
                        Toast.makeText(context, "No data found, try different city or postal code", Toast.LENGTH_LONG).show()
                } else {
                    callback.invoke(null)
                    Toast.makeText(context, "Something went wrong getListLikeString ${response.message()}", Toast.LENGTH_SHORT).show()
                }
                listener.showHideProgressbar(false)
            }

            override fun onFailure(call: Call<ListWeatherCityResponse>, t: Throwable) {
                callback.invoke(null)
                Toast.makeText(context, "Something went wrong getListLikeString $t", Toast.LENGTH_SHORT).show()
                listener.showHideProgressbar(false)
            }
        })
    }
    else {
        listener.loadFromDB()
        adapter.reloadList(listener.getData()!!)
        if(listener.getData()!!.isEmpty())
            Toast.makeText(context, "Last saved result is empty, try reconnect to Network", Toast.LENGTH_LONG).show()

        Toast.makeText(context, "No network available, loading last successful result", Toast.LENGTH_LONG).show()
        listener.showHideProgressbar(false)
    }
}

fun getByZipCode(zipCodePlusCountryCode: String,context: Context, listener: NavigationEventHandler, adapter: CustomAdapter, callback: (item: ListWeatherCityResponse?) -> Unit) {
    listener.showHideProgressbar(true)
    val requestCall = service.getByZipCode(zipCodePlusCountryCode)
    Log.e("requestCall",requestCall.toString())
    if(isNetworkAvailable(context)) {
        requestCall.enqueue(object : Callback<WeatherCityResponse> {
            override fun onResponse(call: Call<WeatherCityResponse>, response: Response<WeatherCityResponse>) {
                if (response.isSuccessful) {
                    val eventsList = response.body()

                    eventsList.let { callback.invoke(ListWeatherCityResponse(listOf(it!!))) }
                    adapter.reloadList(listOf(eventsList!!))
                } else {
                    callback.invoke(null)
                    Toast.makeText(context, "Something went wrong getByZipCode ${response.message()}", Toast.LENGTH_SHORT).show()
                }
                listener.showHideProgressbar(false)
            }

            override fun onFailure(call: Call<WeatherCityResponse>, t: Throwable) {
                callback.invoke(null)
                Toast.makeText(context, "Something went wrong getByZipCode $t", Toast.LENGTH_SHORT).show()
                listener.showHideProgressbar(false)
            }
        })
    }
    else {
        listener.loadFromDB()
        adapter.reloadList(listener.getData()!!)
        Toast.makeText(context, "No network available, loading last successful result", Toast.LENGTH_LONG).show()
        listener.showHideProgressbar(false)
    }
}