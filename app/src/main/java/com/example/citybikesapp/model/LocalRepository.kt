package com.example.citybikesapp.model

import com.example.citybikesapp.model.entity.SavedCity
import com.example.citybikesapp.model.room.SavedDao
import com.example.citybikesapp.model.room.SearchDao

class LocalRepository(
    private val searchDao: SearchDao,
    private val savedDao: SavedDao) {

    suspend fun getIsFav(cityId: String): Boolean{
        val response = savedDao.isCitySaved(cityId)
        return response != 0
    }
    suspend fun addCity(savedCity: SavedCity) = savedDao.insertNewCity(savedCity)
    suspend fun deleteCity(cityId: String) = savedDao.deleteCityById(cityId)
}