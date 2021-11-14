package com.omens.weather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


@Parcelize
data class Wind (
    @SerializedName("speed") val speed: BigDecimal,
    @SerializedName("deg") val deg: BigDecimal
): Parcelable
