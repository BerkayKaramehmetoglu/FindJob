package com.example.findjob.service

import com.example.findjob.model.GetJobsResponse
import com.example.findjob.model.LoginUser
import com.example.findjob.model.PostJob
import com.example.findjob.model.RegisterUser
import com.example.findjob.model.ResponseMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServicesApi {

    @POST("api/insertuser")
    suspend fun registerUser(@Body request: RegisterUser): Response<ResponseMessage>

    @POST("api/signinuser")
    suspend fun loginUser(@Body request: LoginUser): Response<ResponseMessage>

    @POST("api/postjob")
    suspend fun postJob(@Body request: PostJob): Response<ResponseMessage>

    @GET("api/getjobs")
    suspend fun getJobs(): Response<GetJobsResponse>

    @DELETE("api/deletejob/{id}")
    suspend fun deleteJob(@Path("id") id: String): Response<ResponseMessage>

}