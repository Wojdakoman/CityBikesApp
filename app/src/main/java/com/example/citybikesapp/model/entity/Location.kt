package com.example.citybikesapp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    var href: String? = null
) : Parcelable
