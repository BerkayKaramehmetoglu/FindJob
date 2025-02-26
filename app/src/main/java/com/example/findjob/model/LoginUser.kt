package com.example.findjob.model

data class LoginUser(val email: String, val password: String)

data class Result(val success: Boolean, val uid: String, val email: String)