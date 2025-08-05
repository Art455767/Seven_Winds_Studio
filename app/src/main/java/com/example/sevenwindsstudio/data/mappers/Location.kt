package com.example.sevenwindsstudio.data.mappers

import com.example.sevenwindsstudio.data.models.LocationDto
import com.example.sevenwindsstudio.domain.models.Location

fun LocationDto.toDomain(): Location = Location(
    id = id,
    name = name,
    latitude = point.latitude,
    longitude = point.longitude
)