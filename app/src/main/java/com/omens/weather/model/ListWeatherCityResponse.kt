package com.omens.weather.model


import com.google.gson.annotations.SerializedName

data class ListWeatherCityResponse(

    @field:SerializedName("list")
    val list: List<WeatherCityResponse> ? = null,
    )