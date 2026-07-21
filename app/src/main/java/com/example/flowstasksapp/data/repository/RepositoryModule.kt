package com.example.flowstasksapp.data.repository

import com.example.flowstasksapp.data.database.TaskDao
import com.example.flowstasksapp.data.mappers.TaskMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(taskMapper: TaskMapper, taskDao: TaskDao): Repository =
        Repository(taskMapper, taskDao)
}