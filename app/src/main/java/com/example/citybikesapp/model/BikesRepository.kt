package com.example.citybikesapp.model

import android.util.Log
import com.example.citybikesapp.model.api.ApiException
import com.example.citybikesapp.model.api.BikesAPI
import com.example.citybikesapp.model.api.SafeApiRequest
import com.example.citybikesapp.model.entity.APIResponse

class BikesRepository(private val bikesAPI: BikesAPI): SafeApiRequest() {
    suspend fun getAll(): APIResponse? = try {
        apiRequest {
            BikesAPI().getAll()
        }
    } catch (code: ApiException){
        Log.d("[API EXCEPTION]", "c: ${code.message}")
        null
    }
}