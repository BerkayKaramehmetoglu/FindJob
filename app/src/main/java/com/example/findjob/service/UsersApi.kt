package com.example.findjob.service

import com.example.findjob.model.GetUser
import com.example.findjob.model.PostUser
import com.example.findjob.model.ResponseMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsersApi {

    @GET("api/userget")
    suspend fun getUser(): GetUser

    @POST("api/userpost")
    suspend fun postUser(@Body request: PostUser): Response<ResponseMessage>
}