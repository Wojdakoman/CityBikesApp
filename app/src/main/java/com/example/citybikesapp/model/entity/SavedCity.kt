package com.example.citybikesapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savedCities")
class SavedCity(
    val network_id: String,
    val city_name: String,
    val country_name: String,
    val network_name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}