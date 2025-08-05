package com.example.sevenwindsstudio.data.models

data class AuthResponse(
    val token: String,
    val tokenLifeTime: Int
)