package com.example.sevenwindsstudio.domain.models

data class AuthToken(
    val token: String,
    val tokenLifeTime: Int
)