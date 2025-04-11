package com.example.findjob.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findjob.model.UpdateJob
import com.example.findjob.service.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateJobViewModel : ViewModel() {
    val message = mutableStateOf<String?>(null)

    fun updateJob(id: String, jobTitle: String, jobDesc: String, jobPrice: String, state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Services.userService.updateJob(
                    id,
                    UpdateJob(jobTitle, jobDesc, jobPrice, state)
                )
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