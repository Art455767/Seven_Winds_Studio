package com.example.sevenwindsstudio.domain.models

data class MenuItem(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String?,
    val category: String
)