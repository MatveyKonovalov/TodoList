package com.example.myapplication.data.mappers

import com.example.myapplication.domain.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toDomain(category: String): Category {
        return when (category) {
            "Работа" -> Category.Work
            "Личное саморазвитие" -> Category.SelfDevelopment
            "Здоровье и спорт" -> Category.HealthAndFitness
            "Дом и быт" -> Category.HomeAndChores
            "Финансы" -> Category.Finances
            "Семья и друзья" -> Category.FamilyAndFriends
            "Творчество и хобби" -> Category.CreativityAndHobbies
            "Административные дела" -> Category.Errands
            "Отдых и развлечения" -> Category.Leisure
            "Долгосрочные цели" -> Category.Projects
            "Неизвестная" -> Category.Unknown
            else -> {
                try {
                    Category.valueOf(category)
                } catch (e: IllegalArgumentException) {
                    Category.Unknown
                }
            }
        }
    }

    fun toEntity(category: Category): String {
        val result = category.title
        return result
    }
}
