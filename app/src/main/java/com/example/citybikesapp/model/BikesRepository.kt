package com.example.citybikesapp.model

import android.util.Log
import com.example.citybikesapp.model.api.ApiException
import com.example.citybikesapp.model.api.BikesAPI
import com.example.citybikesapp.model.api.SafeApiRequest

class BikesRepository(private val bikesAPI: BikesAPI): SafeApiRequest() {
    suspend fun getAll() = try {
        apiRequest {
            BikesAPI().getAll()
        }
    } catch(code: ApiException){
        Log.d("[API EXCEPTION]", "code: ${code.message}")
    }
}