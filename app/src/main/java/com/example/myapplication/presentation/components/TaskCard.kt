package com.example.myapplication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.Category
import com.example.myapplication.domain.Priority
import com.example.myapplication.domain.Task
import com.example.myapplication.R
import java.nio.file.WatchEvent

@Composable
fun TaskCard(task: Task) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(16))
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(16)
            )

    ) {
        TitleAndCategory(task.title, task.category.title)
        Spacer(modifier = Modifier.size(10.dp))
        Description(task.description)
        Spacer(modifier = Modifier.size(10.dp))
        PriorityAndMake(task.priority)
    }
}

@Composable
private fun TitleAndCategory(title: String, category: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.3f)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 0.dp,
                    topEnd = 0.dp
                )
            )
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 5.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = category,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Description(description: String) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.65f),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            fontSize = 14.sp,
        )
    }

}

@Composable
private fun PriorityAndMake(priority: Priority) {
    // Задаём цвета и текст
    val colorPriority: Color
    val textPriority = when (priority) {
        is Priority.Easy -> {
            colorPriority = Color(0xFF0ABAB5)
            stringResource(R.string.easy_priority)
        }

        is Priority.Medium -> {
            colorPriority = Color(0xFFCC7722)
            stringResource(R.string.medium_priority)
        }

        is Priority.Hard -> {
            colorPriority = Color(0xFFDC143C)
            stringResource(R.string.hard_priority)
        }
    }

    // Делаем контейнер

    // ПОТОМ ИЗМЕНИ СОСТОЯНИЕ
    var isMake by remember { mutableStateOf(false) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    bottomStart = 0.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        Text(text = textPriority, color = colorPriority)
        Switch(
            checked = isMake,
            onCheckedChange = { isMake = !isMake },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onBackground,
                checkedTrackColor = colorPriority,
                uncheckedThumbColor = colorPriority
            )
        )
    }


}

@Preview
@Composable
private fun PreviewTaskCard() {
    TaskCard(
        Task(
            id = "1",
            title = "Созвон по работе",
            description = "Первая работа",
            priority = Priority.Easy,
            category = Category.FamilyAndFriends
        )
    )
}

