package com.example.citybikesapp.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.citybikesapp.model.entity.SavedCity

@Dao
interface SavedDao {
    @Insert
    suspend fun insertNewCity(savedCity: SavedCity)

    @Delete
    suspend fun deleteCity(savedCity: SavedCity)
}