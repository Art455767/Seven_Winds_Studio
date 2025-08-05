package com.example.sevenwindsstudio.domain.useCase

import com.example.sevenwindsstudio.data.api.UserPreferences
import com.example.sevenwindsstudio.domain.models.AuthToken
import com.example.sevenwindsstudio.domain.repository.CoffeeRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: CoffeeRepository,
    private val userPreferences: UserPreferences,
) {
    suspend operator fun invoke(login: String, password: String): Result<AuthToken> {
        return repository.register(login, password).onSuccess {
            userPreferences.saveToken(it.token)
        }
    }
}