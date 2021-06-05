package com.example.citybikesapp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName
import java.util.*

@Parcelize
data class Station(
    @SerializedName("empty_slots")
    val emptySlots: Int,
    val extra: Extra,
    @SerializedName("free_bikes")
    val freeBikes: Int,
    val name: String,
    val timestamp: Date,
    val latitude: Double,
    val longitude: Double
) : Parcelable