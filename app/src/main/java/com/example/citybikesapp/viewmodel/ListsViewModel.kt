package com.example.citybikesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.citybikesapp.model.LocalRepository
import com.example.citybikesapp.model.entity.SavedCity
import com.example.citybikesapp.model.room.AppDatabase
import kotlinx.coroutines.launch

class ListsViewModel(application: Application): AndroidViewModel(application) {
    private val localRepository = LocalRepository(AppDatabase.getDatabase(application).searchDao(), AppDatabase.getDatabase(application).savedDao())
    lateinit var citiesList: LiveData<List<SavedCity>>

    fun loadData(){
        citiesList = localRepository.getSavedCities()
    }

    fun deleteSavedCity(city: String){
        viewModelScope.launch {
            localRepository.deleteSavedCity(city)
        }
    }
}