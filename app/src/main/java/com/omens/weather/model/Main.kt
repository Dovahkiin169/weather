package com.omens.weather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


@Parcelize
data class Main (
    @SerializedName("temp") val temp: BigDecimal,
    @SerializedName("pressure") val pressure: BigDecimal,
    @SerializedName("humidity") val humidity: BigDecimal,
    @SerializedName("temp_min") val temp_min: BigDecimal,
    @SerializedName("temp_max") val temp_max: BigDecimal,
    @SerializedName("feels_like") val feels_like: BigDecimal
): Parcelable
