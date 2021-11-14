package com.omens.weather.utils

import com.omens.weather.model.ListWeatherCityResponse
import com.omens.weather.model.WeatherCityResponse
import java.math.BigDecimal

interface NavigationEventHandler {
    fun showHideProgressbar(visibility: Boolean)
    fun quitApp()

    fun getObject():WeatherCityResponse
    fun setObject(response: WeatherCityResponse)

    fun saveToDB(list: ListWeatherCityResponse)

    fun loadFromDB()

    fun getData() :List<WeatherCityResponse>?

    fun kelvinToCelsius(tempInK: BigDecimal): BigDecimal

    fun navigateToFragment(id: Int)
}