package com.example.findjob.model

data class PostJob(
    val uid: String,
    val jobTitle: String,
    val jobDesc: String,
    val jobPrice: String,
    val jobAdrs: String?,
    val image: String
)
