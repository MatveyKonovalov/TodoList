package com.example.myapplication.data.repository

import com.example.myapplication.data.database.TaskDao
import com.example.myapplication.data.mappers.TaskMapper
import com.example.myapplication.domain.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val taskMapper: TaskMapper,
    private val taskDao: TaskDao
) {
    fun getTasksByDate(date: String): Flow<List<Task>> {
        return taskDao.getTasksByDate(date).map { entities ->
            taskMapper.toDomain(entities)
        }
    }

    suspend fun addTask(task: Task): Long = taskDao.addTask(taskMapper.toEntity(task))
    suspend fun updateTask(task: Task) = taskDao.updateTask(taskMapper.toEntity(task))
    suspend fun deleteTaskById(taskId: Long): Int = taskDao.deleteTask(taskId)
}