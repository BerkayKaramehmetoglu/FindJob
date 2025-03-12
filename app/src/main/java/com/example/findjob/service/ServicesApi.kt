package com.example.findjob.service

import com.example.findjob.model.LoginUser
import com.example.findjob.model.PostJob
import com.example.findjob.model.RegisterUser
import com.example.findjob.model.ResponseMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ServicesApi {

    @POST("api/insertuser")
    suspend fun registerUser(@Body request: RegisterUser): Response<ResponseMessage>

    @POST("api/signinuser")
    suspend fun loginUser(@Body request: LoginUser): Response<ResponseMessage>

    @POST("api/postjob")
    suspend fun postJob(@Body request: PostJob): Response<ResponseMessage>

}