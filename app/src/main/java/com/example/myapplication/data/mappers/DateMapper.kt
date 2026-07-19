package com.example.myapplication.data.mappers

import java.time.LocalDate
import javax.inject.Inject

class DateMapper @Inject constructor() {
    fun toDomain(date: String): LocalDate{
        val (day, month, year) = date.split(".").map{it.toInt()}
        return LocalDate.of(year, month, day)
    }
}