package com.example.flowstasksapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowstasksapp.R
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun WeeklyStrip(curDay: LocalDate, funcSetData: (LocalDate) -> Unit) {
    // Состояние
    val state = rememberWeekCalendarState(
        startDate = LocalDate.now().minusWeeks(52).with(DayOfWeek.MONDAY), // Начало недели
        endDate = LocalDate.now().plusWeeks(52), // Конечная дата
        firstVisibleWeekDate = LocalDate.now(), // Текущая неделя
        firstDayOfWeek = DayOfWeek.MONDAY // Первый день

    )

    // Сам календарь
    WeekCalendar(
        state = state,
        dayContent = { day ->
            DayOfTheWeek(
                dayNum = day.date.dayOfMonth.toString(),
                weekDay = printWeekDay(day.date.dayOfWeek.toString()),
                modifier = Modifier
                    .clickable(onClick = { funcSetData(day.date) }),
                if (curDay == day.date) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.background
            )

        },
        modifier = Modifier
            .height(130.dp)
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(12))
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(12)
            )
            .padding(10.dp),
        weekHeader = { week ->
            val month = week.days.first().date.month

            Text(
                text = printMonth(month.toString()),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp
            )
        }

    )


}

@Composable
private fun printWeekDay(dayOfTheWeekENG: String) = when (dayOfTheWeekENG) {
    "MONDAY" -> stringResource(R.string.monday)
    "TUESDAY" -> stringResource(R.string.tuesday)
    "WEDNESDAY" -> stringResource(R.string.wednesday)
    "THURSDAY" -> stringResource(R.string.thursday)
    "FRIDAY" -> stringResource(R.string.friday)
    "SATURDAY" -> stringResource(R.string.saturday)
    "SUNDAY" -> stringResource(R.string.sunday)
    else -> "Unknown"
}

@Composable
private fun printMonth(titleMonthENG: String) = when (titleMonthENG) {
    "JANUARY" -> stringResource(R.string.january)
    "FEBRUARY" -> stringResource(R.string.february)
    "MARCH" -> stringResource(R.string.march)
    "APRIL" -> stringResource(R.string.april)
    "MAY" -> stringResource(R.string.may)
    "JUNE" -> stringResource(R.string.june)
    "JULY" -> stringResource(R.string.july)
    "AUGUST" -> stringResource(R.string.august)
    "SEPTEMBER" -> stringResource(R.string.september)
    "OCTOBER" -> stringResource(R.string.october)
    "NOVEMBER" -> stringResource(R.string.november)
    "DECEMBER" -> stringResource(R.string.december)
    else -> "Unknown"
}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewWeeklyStrip() {
//    WeeklyStrip()
//}