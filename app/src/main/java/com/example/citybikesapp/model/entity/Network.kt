package com.example.citybikesapp.model.entity

import com.google.gson.annotations.SerializedName

data class Network(
    @SerializedName("company")
    private val any: Any,
    val location: Location,
    val name: String,
    val id: String,
    val href: String,
    val stations: List<Station> = emptyList()
) {
    val company: List<String>
    get() =
        if(any is List<*>)
            if(any.firstOrNull() is String) any as List<String>
            else emptyList()
        else if(any is String) listOf(any.toString())
        else emptyList()
}
