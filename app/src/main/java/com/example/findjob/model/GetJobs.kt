package com.example.findjob.model

data class GetJobs(
    val jobTitle: String,
    val downloadUrl: String,
    val jobPrice: String,
    val jobDesc: String,
    val uid: String,
    val jobAdrs: String,
    val currentTime: String
)

