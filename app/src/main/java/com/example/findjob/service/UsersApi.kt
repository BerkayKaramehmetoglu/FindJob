package com.example.findjob.service

import com.example.findjob.model.GetUser
import com.example.findjob.model.PostUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsersApi {

    @GET("api/userget")
    suspend fun getUser(): GetUser

    @POST("api/userpost")
    suspend fun postUser(@Body request: PostUser)
}