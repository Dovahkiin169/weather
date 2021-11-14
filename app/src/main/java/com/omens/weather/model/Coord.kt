package com.omens.weather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


@Parcelize
data class Coord (
    @SerializedName("lon") val lon: BigDecimal,
    @SerializedName("lat") val lat: BigDecimal
): Parcelable
