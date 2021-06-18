package com.example.citybikesapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citybikesapp.model.BikesRepository
import com.example.citybikesapp.model.LocalRepository
import com.example.citybikesapp.model.api.BikesAPI
import com.example.citybikesapp.model.entity.*
import com.example.citybikesapp.model.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasicViewModel(application: Application): AndroidViewModel(application) {
    private val repository: BikesRepository = BikesRepository(BikesAPI())
    private val localRepository = LocalRepository(AppDatabase.getDatabase(application).searchDao(), AppDatabase.getDatabase(application).savedDao())



    var apiResponse = MutableLiveData<APIResponse>() //odpowiedź z API
    val isFav = MutableLiveData<Boolean>()
    var network = MutableLiveData<APIResponse2>()

    //ustawienie odpowiedzi z API
    fun setAPIResponse(){
        val response = MutableLiveData<APIResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getAll()
            response.postValue(arrivedData!!)
        }
        apiResponse = response
    }

    fun getNetworkInfo(path: String){
        viewModelScope.launch {
            network.value = repository.getNetworkInfo(path)
            //check if city is saved
            isFav.postValue(localRepository.getIsFav(network.value?.network?.id?:"null"))
        }
    }

    //add to fav btn click
    fun handleFavClick(){
        viewModelScope.launch {
            if(isFav.value!!)
                localRepository.deleteSavedCity(network.value?.network?.id!!)
            else
                localRepository.addSavedCity(SavedCity(network.value?.network?.id!!, network.value?.network?.location?.city!!, network.value?.network?.location?.country!!, network.value?.network?.name!!))
            isFav.postValue(!isFav.value!!)
        }
    }


}