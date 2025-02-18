package com.example.findjob.model

data class GetUser(
    val email: String = "Boş",
    val password: String = "Boş",
    val age: Int = 0,
    val city: String = "Boş"
)