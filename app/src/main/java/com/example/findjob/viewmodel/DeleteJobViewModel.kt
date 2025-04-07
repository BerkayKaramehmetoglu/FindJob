package com.example.findjob.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findjob.service.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteJobViewModel : ViewModel() {
    val message = mutableStateOf<String?>(null)

    fun deleteJob(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Services.userService.deleteJob(id)
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