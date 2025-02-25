package com.example.findjob.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Services {

    private const val BASE_URL = "http://10.0.2.2:3000/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userService: ServicesApi by lazy {
        retrofit.create(ServicesApi::class.java)
    }
}