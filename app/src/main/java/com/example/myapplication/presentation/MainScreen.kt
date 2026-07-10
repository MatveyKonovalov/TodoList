package com.example.myapplication.presentation

import android.R.attr.textSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.components.WeeklyStrip
import com.example.myapplication.R
import com.example.myapplication.domain.Category
import com.example.myapplication.domain.Priority
import com.example.myapplication.domain.Task
import com.example.myapplication.presentation.components.TaskCard

@Composable
fun MainScreen() {
    // типо таски
    val tasks = listOf(
        Task(
            title = "Созвон",
            description = "Работа Yandex. Mobile",
            category = Category.Work
        ),
        Task(
            title = "Прогулка",
            description = "Погулять с собакой",
            category = Category.FamilyAndFriends,
            priority = Priority.Medium
        )
    )
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 40.dp)
    ) {
        item { Title() }
        items(items = tasks, key = { task -> task.id }) { task ->
            TaskCard(task)
        }

    }
}

@Composable
private fun Title() {
    Column {
        // Вывод заголовка секции(Календарь)
        Text(
            text = stringResource(R.string.calendar),
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
        )
        // Недельная строка
        WeeklyStrip((1..7).toList())

        // Список дел
        Text(
            text = stringResource(R.string.todo_list),
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )
    }
}
