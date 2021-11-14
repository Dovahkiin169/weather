package com.omens.weather.model


import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class WeatherCityResponse(

    @field:SerializedName("coord")
    val coord: Coord ? = null,

    @field:SerializedName("weather")
    val weather: List<Weather?>? = null,

    @field:SerializedName("base")
    val base: String ? = "",

    @field:SerializedName("main")
    val main: Main? = null,

    @field:SerializedName("visibility")
    val visibility: String ? = "",

    @field:SerializedName("wind")
    val wind: Wind? = null,

    @field:SerializedName("clouds")
    val clouds: Clouds? = null,

    @field:SerializedName("dt")
    val dt: BigInteger? = BigInteger.valueOf(0),

    @field:SerializedName("sys")
    val sys: Sys ? = null,

    @field:SerializedName("timezone")
    val timezone: String ? = "",

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("cod")
    val cod: BigInteger? = BigInteger.valueOf(0)
    )