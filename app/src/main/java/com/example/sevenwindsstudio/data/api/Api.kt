package com.example.sevenwindsstudio.data.api

import com.example.sevenwindsstudio.data.models.AuthRequest
import com.example.sevenwindsstudio.data.models.AuthResponse
import com.example.sevenwindsstudio.data.models.LocationDto
import com.example.sevenwindsstudio.data.models.MenuItemDto
import retrofit2.Response
import retrofit2.http.*

interface CoffeeApi {
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/register") suspend fun register(@Body request: AuthRequest): Response<AuthResponse>

    @GET("locations")
    suspend fun getLocations(@Header("Authorization") token: String): Response<List<LocationDto>>

    @GET("location/{id}/menu")
    suspend fun getMenu(@Header("Authorization") token: String, @Path("id") locationId: Int):
            Response<List<MenuItemDto>>
}