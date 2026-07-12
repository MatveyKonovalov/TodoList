package com.example.myapplication.domain

import androidx.compose.runtime.Immutable
import java.util.UUID


sealed class Priority {
    object Easy : Priority()
    object Medium : Priority()
    object Hard : Priority()
}

enum class Category(val title: String){
   Work("Работа"),
    SelfDevelopment("Личное саморазвитие"),
    HealthAndFitness("Здоровье и спорт"),
    HomeAndChores("Дом и быт"),
    Finances("Финансы"),
    FamilyAndFriends("Семья и друзья"),
    CreativityAndHobbies("Творчество и хобби"),
    Errands("Административные дела"),
    Leisure("Отдых и развлечения"),
    Projects("Долгосрочные цели"),
    Unknown("Неизвестная")
}
@Immutable
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val priority: Priority = Priority.Easy,
    val category: Category
)