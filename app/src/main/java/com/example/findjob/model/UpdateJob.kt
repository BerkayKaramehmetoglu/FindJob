package com.example.findjob.model

data class UpdateJob(
    val jobTitle: String,
    val jobDesc: String,
    val jobPrice: String,
    val state: Boolean,
)
