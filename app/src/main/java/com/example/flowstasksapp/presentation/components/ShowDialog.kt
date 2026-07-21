package com.example.flowstasksapp.presentation.components


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.flowstasksapp.domain.Category
import com.example.flowstasksapp.domain.Priority
import com.example.flowstasksapp.domain.Task
import java.time.LocalDate
import com.example.flowstasksapp.R

@Composable
fun ShowDialog(
    onDismissFunc: () -> Unit,
    confirmButton: (Task) -> Unit,
    curDate: LocalDate,
    isError: Boolean,
    funcShowError: () -> Unit,

    task: Task? = null,
    screenTitle: String
) {
    // Параметры для отображения данных о задаче
    val title = rememberSaveable { mutableStateOf(task?.title ?: "") }
    val description = rememberSaveable { mutableStateOf(task?.description ?: "") }
    val selectedCategory = remember { mutableStateOf(task?.category ?: Category.Unknown) }
    val priorities = listOf(Priority.Easy, Priority.Medium, Priority.Hard)
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(
            task?.priority ?: priorities[0]
        )
    }

    // Вывод при ошибке
    val message = stringResource(R.string.title_required)
    val backgroundColor = MaterialTheme.colorScheme.primaryContainer.toArgb()
    val textColor = MaterialTheme.colorScheme.onBackground.toArgb()
    val context = LocalContext.current
    LaunchedEffect(isError) {
        if (isError) {
            showCustomToast(context, message, backgroundColor, textColor)
        }
    }

    // Само окно
    Dialog(
        onDismissRequest = onDismissFunc,
        properties = DialogProperties(
            usePlatformDefaultWidth = false // Сделаем свои кастомные размеры
        )
    ) {
        Card( // Логическая группирова
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Заголовок
                Text(
                    text = screenTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Содержимое
                Content(
                    title = title.value,
                    description = description.value,
                    selectedCategory = selectedCategory.value,
                    selectedOpinion = selectedOption,
                    onTitleChange = { newText: String -> title.value = newText },
                    onDescriptionChange = { newDescription: String ->
                        description.value = newDescription
                    },
                    onCategoryChange = { category: Category -> selectedCategory.value = category },
                    onPriorityChange = onOptionSelected,
                    isError
                )

                // Управляющие кнопки
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    // Кнопка отмены
                    TextButton(onClick = onDismissFunc) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }

                    // Кнопка сохранения
                    TextButton(onClick = {
                        if (title.value.isNotBlank()) {
                            val newTask = getTask(
                                task = task,
                                title = title.value,
                                description = description.value,
                                priority = selectedOption,
                                category = selectedCategory.value,
                                date = curDate
                            )
                            confirmButton(newTask) // Сохраняем/Обновляем задачу
                        } else {
                            funcShowError() // Выводим сообщение об ошибке
                            Log.w("AddTask", "Validation failed: title=${title.value}")
                        }
                    }
                    ) {
                        Text(
                            text = stringResource(R.string.add_task),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

private fun getTask(
    task: Task?,
    title: String,
    description: String,
    priority: Priority,
    category: Category,
    date: LocalDate
): Task {
    return task?.copy(
        title = title,
        description = description.trim().take(50),
        priority = priority,
        category = category,
        date = date
    )
        ?: Task(
            title = title,
            description = description.trim().take(50),
            priority = priority,
            category = category,
            date = date
        )
}