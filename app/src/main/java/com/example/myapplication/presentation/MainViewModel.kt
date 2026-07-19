package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow


class MainViewModel : ViewModel() {
    private val _tasks = MutableStateFlow(listOf<Task>())
}