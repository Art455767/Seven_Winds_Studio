package com.example.sevenwindsstudio.data.repository

import com.example.sevenwindsstudio.data.api.CoffeeApi
import com.example.sevenwindsstudio.data.mappers.toDomain
import com.example.sevenwindsstudio.data.models.AuthRequest
import com.example.sevenwindsstudio.domain.models.AuthToken
import com.example.sevenwindsstudio.domain.models.Location
import com.example.sevenwindsstudio.domain.models.MenuItem
import com.example.sevenwindsstudio.domain.repository.CoffeeRepository
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
    private val api: CoffeeApi) : CoffeeRepository {

    override suspend fun login(login: String, password: String): Result<AuthToken> {
        return try {
            val response = api.login(AuthRequest(login, password))
            if (response.isSuccessful) {
                val body = response.body()!!
                Result.success(AuthToken(body.token, body.tokenLifeTime))
            } else {
                Result.failure(Exception("Error login: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(login: String, password: String): Result<AuthToken> {
        return try {
            val response = api.register(AuthRequest(login, password))
            if (response.isSuccessful) {
                val body = response.body()!!
                Result.success(AuthToken(body.token, body.tokenLifeTime))
            } else {
                Result.failure(Exception("Error register: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLocations(token: String): Result<List<Location>> {
        return try {
            val response = api.getLocations("Bearer $token")
            if (response.isSuccessful) {
                val list = response.body() ?: emptyList()
                Result.success(list.map { it.toDomain() })
            } else {
                Result.failure(Exception("Error getting locations: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMenu(token: String, locationId: Int): Result<List<MenuItem>> {
        return try {
            val response = api.getMenu("Bearer $token", locationId)
            if (response.isSuccessful) {
                val list = response.body() ?: emptyList()
                Result.success(list.map {
                    MenuItem(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.imageURL,
                        price = it.price,
                        category = it.category ?: ""
                    )
                })
            } else {
                Result.failure(Exception("Error getting menu: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}