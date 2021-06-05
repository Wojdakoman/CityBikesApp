package com.example.citybikesapp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Extra(
    val address: String,
    val slots: Int,
    val uid: Int
) : Parcelable