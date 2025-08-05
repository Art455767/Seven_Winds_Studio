package com.example.sevenwindsstudio.data.models

data class MenuItemDto(
    val id: Int,
    val name: String,
    val imageURL: String,
    val price: Int,
    val category: String? = null
)