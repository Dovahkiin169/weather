package com.omens.weather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.math.BigInteger


@Parcelize
data class Sys (
    @SerializedName("type") val type: BigDecimal,
    @SerializedName("id") val id: BigInteger,
    @SerializedName("message") val message: BigDecimal,
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: BigInteger,
    @SerializedName("sunset") val sunset: BigInteger
): Parcelable
