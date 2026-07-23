package com.example.flowstasksapp.data.repository

import com.example.flowstasksapp.data.database.TaskDao
import com.example.flowstasksapp.data.mappers.TaskMapper
import com.example.flowstasksapp.domain.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(repository: Repository): RepositoryImpl
}