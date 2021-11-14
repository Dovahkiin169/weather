package com.omens.weather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


@Parcelize
data class Clouds (
    @SerializedName("all") val all: BigDecimal
): Parcelable
