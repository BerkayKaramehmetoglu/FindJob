package com.example.findjob.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.findjob.datastore.saveUserSession
import com.example.findjob.model.LoginUser
import com.example.findjob.service.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginUserViewModel(application: Application) : AndroidViewModel(application)  {

    val message = mutableStateOf<String?>(null)
    val success = mutableStateOf<Boolean?>(null)
    val uid = mutableStateOf<String?>(null)

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    Services.userService.loginUser(LoginUser(email, password))
                if (response.isSuccessful) {
                    message.value = response.body()?.message
                    success.value = response.body()?.result?.success
                    uid.value = response.body()?.result?.uid
                    uid.value?.let { saveUserSession(getApplication(), it) }
                } else {
                    message.value = response.body()?.message
                }
            } catch (e: Exception) {
                println("Bağlantı hatası: ${e.localizedMessage}")
            }
        }
    }
}