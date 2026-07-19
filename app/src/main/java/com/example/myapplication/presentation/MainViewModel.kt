package com.example.myapplication.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.domain.Task
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

//    private val _date = MutableStateFlow<String>("")
//    val date = _date.asStateFlow()
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun loadTaskByDate(date: String) { // Загрузка задач по дате
        viewModelScope.launch {
            _isLoading.value = true

            try {
                repository.getTasksByDate(date).collect { taskList ->
                    _tasks.value = taskList
                    _isLoading.value = false
                    Log.d("Success in view model: loadTaskByDate", "Tasks have been loaded")
                }

            } catch (e: Exception) {
                Log.d("Error in ViewModel: loadTaskById", e.message.toString())
                _isLoading.value = false
            }
        }

    }

    fun addTask(task: Task) { // Добавление задачи
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    repository.addTask(task)
                }.onFailure {
                    Log.d("Error in view model: addAlarm", it.message.toString())
                }.onSuccess {
                    Log.d("Success in view model: addTask", "Task has been added")
                }

            }
        }
    }

    fun updateTask(task: Task) { // Обновление задачи
        viewModelScope.launch {

            runCatching {
                repository.updateTask(task)
            }.onFailure {
                Log.d("Error in view model: updateAlarm", it.message.toString())
            }.onSuccess {
                Log.d("Success in view model: updateAlarm", "Task has been updated")
            }

        }
    }

    fun deleteTask(taskId: Long) { // Удаление задачи
        viewModelScope.launch {
            runCatching {
                repository.deleteTaskById(taskId)
            }.onFailure {
                Log.d("Error in view model: deleteTask", it.message.toString())
            }.onSuccess {
                Log.d("Success in view model: deleteTask", "Task has been deleted")
            }

        }
    }

    fun setDate(date: String) {
        loadTaskByDate(date)
    }
}