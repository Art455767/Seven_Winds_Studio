package com.example.sevenwindsstudio.domain.repository

import com.example.sevenwindsstudio.domain.models.AuthToken
import com.example.sevenwindsstudio.domain.models.Location
import com.example.sevenwindsstudio.domain.models.MenuItem

interface CoffeeRepository {

    suspend fun login(login: String, password: String): Result<AuthToken>
    suspend fun register(login: String, password: String): Result<AuthToken>
    suspend fun getLocations(token: String): Result<List<Location>>
    suspend fun getMenu(token: String, locationId: Int): Result<List<MenuItem>>
}