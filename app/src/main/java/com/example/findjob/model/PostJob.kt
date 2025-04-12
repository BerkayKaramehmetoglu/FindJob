package com.example.findjob.model

data class PostJob(
    val uid: String,
    val jobTitle: String,
    val jobDesc: String,
    val jobPrice: String,
    val image: String,
    val latitude: Double,
    val longitude: Double
)
