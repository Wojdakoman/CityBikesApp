package com.example.citybikesapp.model.entity

import com.google.gson.annotations.SerializedName
import java.util.*

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
) {
}