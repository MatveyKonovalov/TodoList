package com.example.myapplication.data.mappers

import com.example.myapplication.domain.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    fun toDomain(category: String): Category {
        val result = Category.valueOf(category)
        return result
    }

    fun toEntity(category: Category): String{
        val result = category.title
        return result
    }
}
