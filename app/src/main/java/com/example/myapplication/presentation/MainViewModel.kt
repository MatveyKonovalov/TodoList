package com.example.myapplication.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.domain.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _isOpenAddScreen = MutableStateFlow(false)
    val isOpenAddScreen = _isOpenAddScreen.asStateFlow()
    private val _date = MutableStateFlow<LocalDate>(LocalDate.now())
    val date = _date.asStateFlow()
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
            closeAddScreen()
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

    fun setDate(date: LocalDate) { // format: dd.mm.yyyy
        _date.value = date
        val year = date.year
        val month = date.monthValue
        val day = date.dayOfMonth
        loadTaskByDate(String.format("%02d.%02d.%04d", day, month, year))
    }

    fun openAddScreen(){
        _isOpenAddScreen.value = true
    }
    fun closeAddScreen(){
        _isOpenAddScreen.value = false
    }
}