package com.example.citybikesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.citybikesapp.model.BikesRepository
import com.example.citybikesapp.model.api.BikesAPI
import com.example.citybikesapp.model.entity.APIResponse
import com.example.citybikesapp.model.entity.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasicViewModel(application: Application): AndroidViewModel(application) {
    private val repository: BikesRepository = BikesRepository(BikesAPI())

    var apiResponse = MutableLiveData<APIResponse>()

    fun setAPIResponse(){
        val response = MutableLiveData<APIResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            val arrivedData = repository.getAll()
            response.postValue(arrivedData!!)
        }
        apiResponse = response
    }
}