package com.omens.weather.service

import com.omens.weather.model.ListWeatherCityResponse
import com.omens.weather.model.WeatherCityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("find")
    fun getListOfCities (
        @Query("mode") mode: String? = "json",
        @Query("type") type: String? = "like",
        @Query("q") city: String,
        @Query("APPID") appID: String? = "292c7f0b0e013fe29b284b35e266689d"
    ) : Call<ListWeatherCityResponse>

    @GET("weather")
    fun getByZipCode (
        @Query("zip") zipCodePlusCountryCode: String,
        @Query("APPID") appID: String? = "292c7f0b0e013fe29b284b35e266689d"
    ) : Call<WeatherCityResponse>
}