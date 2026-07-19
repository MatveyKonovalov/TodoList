package com.example.myapplication.data.mappers

import com.example.myapplication.domain.Priority
import javax.inject.Inject

class PriorityMapper @Inject constructor(){
    fun toDomain(title: String): Priority{
        return when(title){
            "easy" -> Priority.Easy
            "medium" -> Priority.Medium
            "hard" -> Priority.Hard
            else -> throw Exception("Undefined title priority in DataBase")
        }
    }

    fun toEntity(priority: Priority): String{
        return when(priority){
            Priority.Easy -> "easy"
            Priority.Medium -> "medium"
            Priority.Hard -> "hard"
        }
    }

}