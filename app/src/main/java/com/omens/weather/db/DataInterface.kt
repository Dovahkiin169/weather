package com.omens.weather.db

import com.omens.weather.model.ListWeatherCityResponse

interface DataInterface {
    interface View {
        fun setData(outputList: ListWeatherCityResponse?)
    }

    interface UserActionsListener {
        fun fetchDataFormDB()
        fun saveDataToDb(data: MutableMap<String, Any>)
    }
}