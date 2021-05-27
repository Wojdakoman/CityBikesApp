package com.example.citybikesapp.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.citybikesapp.model.entity.SearchResult

@Dao
interface SearchDao {
    @Insert
    suspend fun insertNewCity(searchResult: SearchResult)

    @Delete
    suspend fun deleteCity(searchResult: SearchResult)
}