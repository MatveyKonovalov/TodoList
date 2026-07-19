package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.Task
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _tasks = MutableStateFlow(listOf<Task>())
    val tasks = _tasks.asStateFlow()
}