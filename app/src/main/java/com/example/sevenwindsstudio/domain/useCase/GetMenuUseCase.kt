package com.example.sevenwindsstudio.domain.useCase

import com.example.sevenwindsstudio.domain.models.MenuItem
import com.example.sevenwindsstudio.domain.repository.CoffeeRepository
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    suspend operator fun invoke(token: String, locationId: Int): Result<List<MenuItem>> {
        return repository.getMenu(token, locationId)
    }
}