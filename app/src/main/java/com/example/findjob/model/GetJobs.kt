package com.example.findjob.model

data class GetJobs(
    val id: String,
    val jobTitle: String,
    val downloadUrl: String,
    val jobPrice: String,
    val jobDesc: String,
    val uid: String,
    val jobCity: String,
    val currentTime: String,
    val state: Boolean
)