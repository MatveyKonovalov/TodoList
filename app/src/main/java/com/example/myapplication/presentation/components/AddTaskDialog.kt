package com.example.myapplication.presentation.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.R
import com.example.myapplication.domain.Category
import com.example.myapplication.domain.Priority
import com.example.myapplication.domain.Task
import java.time.LocalDate

@Composable
fun AddTask(
    onDismissFunc: () -> Unit,
    confirmButton: (Task) -> Unit,
    curDate: LocalDate,
    isErrorAdd: Boolean,
    funcShowError: () -> Unit
) {
    // Параметры для создания новой задачи
    val title = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf(Category.Unknown) }
    val priorities = listOf(Priority.Easy, Priority.Medium, Priority.Hard)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(priorities[0]) }

    // Вывод при ошибке
    val message = stringResource(R.string.error_in_make_toast)
    val backgroundColor = MaterialTheme.colorScheme.primaryContainer.toArgb()
    val textColor = MaterialTheme.colorScheme.onBackground.toArgb()
    val context = LocalContext.current
    LaunchedEffect(isErrorAdd) {
        if (isErrorAdd) {
            showCustomToast(context, message, backgroundColor, textColor)
        }
    }


    Dialog(
        onDismissRequest = onDismissFunc,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
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
                    text = stringResource(R.string.add_task),
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
                    isErrorAdd
                )

                // Кнопки
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismissFunc
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }

                    TextButton(
                        onClick = {
                            if (title.value.isNotBlank()) {
                                val newTask = Task(
                                    title = title.value.trim().take(15),
                                    description = description.value.trim(),
                                    priority = selectedOption,
                                    category = selectedCategory.value,
                                    date = curDate
                                )

                                confirmButton(newTask)
                                onDismissFunc()
                            } else {
                                funcShowError()
                                Log.w(
                                    "AddTask",
                                    "Validation failed: title=${title.value}, category=${selectedCategory.value}"
                                )
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.add_task),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

// Вывод всплывающего окна, говорящего об ошибке
private fun showCustomToast(
    context: Context,
    message: String,
    backgroundColor: Int,
    textColor: Int
) {
    val toast = Toast(context)

    val textView = TextView(context).apply {
        text = message
        setTextColor(textColor)
        setPadding(48, 24, 48, 24)
        textSize = 14f
        setBackgroundColor(backgroundColor)

        // Создание закругления
        val drawable = GradientDrawable().apply {
            setColor(backgroundColor)
            cornerRadius = 16f
        }
        background = drawable
    }

    toast.view = textView
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(Gravity.BOTTOM, 0, 100)
    toast.show()
}

@Composable
private fun Content(
    title: String,
    description: String,
    selectedCategory: Category,
    selectedOpinion: Priority,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    isErrorAdd: Boolean
) {

    // Основное содержимое
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Compartment(stringResource(R.string.title_description)) {
            val borderColor =
                if (isErrorAdd) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.onBackground
            OutlinedTextField(
                value = title,
                onValueChange = { newText -> onTitleChange(newText) },
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_title),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = borderColor
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences, // Каждое предложение с большой буквы
                    keyboardType = KeyboardType.Text
                )

            )

            OutlinedTextField(
                value = description,
                onValueChange = { newText -> onDescriptionChange(newText) },
                placeholder = { Text(text = stringResource(R.string.enter_description)) },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences, // Каждое предложение с большой буквы
                    keyboardType = KeyboardType.Text
                )
            )
        }

        Compartment(stringResource(R.string.priority)) {
            val priorities = listOf(Priority.Easy, Priority.Medium, Priority.Hard)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                priorities.forEach { priority ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = priority == selectedOpinion,
                            onClick = { onPriorityChange(priority) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = getColorByPriority(priority)
                            )
                        )
                        Text(
                            text = getPriorityText(priority),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        Compartment(stringResource(R.string.category_required)) {
            CategoryHorizontalSelector(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    onCategoryChange(category)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun CategoryHorizontalSelector(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = Category.entries

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        categories.forEach { category ->
            Card(
                modifier = Modifier
                    .width(110.dp)
                    .height(90.dp)
                    .border(
                        2.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (category == selectedCategory) {
                        Color(0xFF293133)
                    } else {
                        MaterialTheme.colorScheme.background
                    }
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (category == selectedCategory) 4.dp else 1.dp
                ),
                onClick = { onCategorySelected(category) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = when (category) {
                            Category.Work -> "💼"
                            Category.SelfDevelopment -> "📚"
                            Category.HealthAndFitness -> "💪"
                            Category.HomeAndChores -> "🏠"
                            Category.Finances -> "💰"
                            Category.FamilyAndFriends -> "👨‍👩‍👧‍👦"
                            Category.CreativityAndHobbies -> "🎨"
                            Category.Errands -> "📋"
                            Category.Leisure -> "🎮"
                            Category.Projects -> "🚀"
                            Category.Unknown -> "❓"
                        },
                        fontSize = 32.sp
                    )
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                }
            }
        }
    }
}

@Composable
private fun getPriorityText(priority: Priority): String {
    return when (priority) {
        Priority.Easy -> stringResource(R.string.easy_priority)
        Priority.Medium -> stringResource(R.string.medium_priority)
        Priority.Hard -> stringResource(R.string.hard_priority)
    }
}

@Composable
private fun Compartment(
    title: String,
    backgroundColor: Color = Color(0xFF1c1c1c),
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun getColorByPriority(priority: Priority): Color {
    return when (priority) {
        Priority.Easy -> Color(0xFF0ABAB5)
        Priority.Medium -> Color(0xFFCC7722)
        Priority.Hard -> Color(0xFFDC143C)
    }
}