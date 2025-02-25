package com.example.findjob.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findjob.model.RegisterUser
import com.example.findjob.service.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterUserViewModel : ViewModel() {
    val message = mutableStateOf<String?>(null)

    fun registerUser(email: String, password: String, rpassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    Services.userService.registerUser(RegisterUser(email, password, rpassword))
                if (response.isSuccessful) {
                    message.value = response.body()?.message
                } else {
                    message.value = response.body()?.message
                }
            } catch (e: Exception) {
                println("Bağlantı hatası: ${e.localizedMessage}")
            }
        }
    }
}