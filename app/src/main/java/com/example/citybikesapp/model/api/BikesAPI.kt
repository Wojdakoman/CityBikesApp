package com.example.citybikesapp.model.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://api.citybik.es/v2/"

interface BikesAPI {
    companion object {
        operator fun invoke(): BikesAPI {
            val client = OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BikesAPI::class.java)
        }
    }
}