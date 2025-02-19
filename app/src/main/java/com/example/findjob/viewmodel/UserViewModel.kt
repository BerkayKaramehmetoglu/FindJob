package com.example.findjob.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findjob.model.GetUser
import com.example.findjob.model.PostUser
import com.example.findjob.service.UsersApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserViewModel : ViewModel() {
    private val BASE_URL = "http://10.0.2.2:3000/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val user = retrofit.create(UsersApi::class.java)

    val getUser = mutableStateOf(GetUser())

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = user.getUser()
                getUser.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val loginMessage = mutableStateOf<String?>(null)

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = user.postUser(PostUser(email, password))
                if (response.isSuccessful) {
                    loginMessage.value = response.body()?.message
                    println(response.body()?.message)
                } else {
                    loginMessage.value = response.body()?.message
                    println(response.body()?.message)
                }
            } catch (e: Exception) {
                println("Bağlantı hatası: ${e.localizedMessage}")
            }
        }
    }
}