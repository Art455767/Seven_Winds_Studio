package com.example.sevenwindsstudio.data.di

import com.example.sevenwindsstudio.data.repository.CoffeeRepositoryImpl
import com.example.sevenwindsstudio.domain.repository.CoffeeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCoffeeRepository(coffeeRepositoryImpl: CoffeeRepositoryImpl): CoffeeRepository
}