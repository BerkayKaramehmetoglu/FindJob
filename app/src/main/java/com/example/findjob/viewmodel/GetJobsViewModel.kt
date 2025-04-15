package com.example.findjob.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findjob.datastore.getUserSession
import com.example.findjob.model.GetJobs
import com.example.findjob.service.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetJobsViewModel : ViewModel() {

    val jobsList = mutableStateOf<List<GetJobs>>(emptyList())

    fun getJobs(user: Boolean = false, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Services.userService.getJobs()
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        if (user) {
                            val filteredJobs = it.filter { jobs -> jobs.uid == getUserSession(context) }
                            jobsList.value = filteredJobs
                        } else {
                            val filteredAllJobs = it.filter{jobs -> jobs.state }
                            jobsList.value = filteredAllJobs
                        }
                    } ?: run {
                        println("Veri bulunamadı veya boş.")
                    }
                } else {
                    println("API çağrısı başarısız: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Bağlantı hatası: ${e.localizedMessage}")
            }
        }
    }
}