package com.example.citybikesapp.model

import android.util.Log
import com.example.citybikesapp.model.api.ApiException
import com.example.citybikesapp.model.api.BikesAPI
import com.example.citybikesapp.model.api.SafeApiRequest
import com.example.citybikesapp.model.entity.APIResponse
import com.example.citybikesapp.model.entity.APIResponse2
import com.example.citybikesapp.model.entity.Location
import com.example.citybikesapp.model.entity.Network
import retrofit2.Call
import retrofit2.awaitResponse

class BikesRepository(private val bikesAPI: BikesAPI): SafeApiRequest() {
    suspend fun getAll(): APIResponse? = try {
        apiRequest {
            BikesAPI().getAll()
        }
    } catch (code: ApiException){
        Log.d("[API EXCEPTION]", "c: ${code.message}")
        null
    }

    suspend fun getNetworkInfo(path: String): APIResponse2?{
        return BikesAPI().getNetwork(path).body()
    }


}