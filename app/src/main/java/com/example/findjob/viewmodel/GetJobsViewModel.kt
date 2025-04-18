package com.example.findjob.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findjob.datastore.getFavJobs
import com.example.findjob.datastore.getUserSession
import com.example.findjob.model.GetJobs
import com.example.findjob.service.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetJobsViewModel : ViewModel() {
    val myJobsList = mutableStateOf<List<GetJobs>>(emptyList())
    val favJobsList = mutableStateOf<List<GetJobs>>(emptyList())
    val allJobsList = mutableStateOf<List<GetJobs>>(emptyList())

    fun getJobs(user: Boolean = false, fav: Boolean = false, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Services.userService.getJobs()
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        if (user) {
                            val filteredJobs =
                                it.filter { jobs -> jobs.uid == getUserSession(context) }
                            myJobsList.value = filteredJobs
                        } else {
                            if (fav) {
                                val favIds = getFavJobs(context)
                                val filteredFavJobs =
                                    it.filter { jobs -> jobs.id in favIds }
                                favJobsList.value = filteredFavJobs
                            } else {
                                val filteredAllJobs = it.filter { jobs -> jobs.state }
                                allJobsList.value = filteredAllJobs
                            }
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