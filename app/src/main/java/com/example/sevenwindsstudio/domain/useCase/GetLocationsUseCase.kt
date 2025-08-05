package com.example.sevenwindsstudio.domain.useCase

import com.example.sevenwindsstudio.domain.models.Location
import com.example.sevenwindsstudio.domain.repository.CoffeeRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    suspend operator fun invoke(token: String): Result<List<Location>> {
        return repository.getLocations(token)
    }
}