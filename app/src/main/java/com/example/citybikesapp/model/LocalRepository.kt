package com.example.citybikesapp.model

import com.example.citybikesapp.model.room.SavedDao
import com.example.citybikesapp.model.room.SearchDao

class LocalRepository(
    private val searchDao: SearchDao,
    private val savedDao: SavedDao) {
}