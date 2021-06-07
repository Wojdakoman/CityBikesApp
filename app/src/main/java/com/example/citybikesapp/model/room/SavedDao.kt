package com.example.citybikesapp.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.citybikesapp.model.entity.SavedCity

@Dao
interface SavedDao {
    @Insert
    suspend fun insertNewCity(savedCity: SavedCity)

    @Delete
    suspend fun deleteCity(savedCity: SavedCity)

    @Query("SELECT COUNT(*) FROM savedCities WHERE network_id = :id")
    suspend fun isCitySaved(id: String): Int

    @Query("DELETE FROM savedCities WHERE network_id = :id")
    suspend fun deleteCityById(id: String)
}