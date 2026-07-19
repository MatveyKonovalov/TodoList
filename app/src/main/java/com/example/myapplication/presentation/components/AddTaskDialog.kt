package com.example.myapplication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.contentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.domain.Category
import com.example.myapplication.domain.Priority
import com.example.myapplication.domain.Task

@Composable
fun AddTask(onDismissFunc: () -> Unit, confirmButton: (Task) -> Unit) {
    val task = rememberSaveable { mutableStateOf<Task?>(null) }

    // Основные критерии
    val title = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") } // Необязательный параметр
    // Дата заполняется автоматически
    val selectedCategory = remember { mutableStateOf(Category.Unknown) }
    // Выбор приоритета
    val priorities = listOf(Priority.Easy, Priority.Medium, Priority.Hard)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(priorities[0]) }


    AlertDialog(
        onDismissRequest = onDismissFunc,
        title = { Text(stringResource(R.string.add_task)) },
        text = {
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
                onPriorityChange = onOptionSelected
            )
        },
        confirmButton = {
            if (title.value != ""){
                task.value = Task(
                    title = title.value,
                    description = description.value,
                    priority = selectedOption,
                    category = selectedCategory.value,
                )
            }
            TextButton(
                onClick = { if (task.value == null) onDismissFunc() else confirmButton(task.value as Task) },
            ) {
                Text(text = stringResource(R.string.add_task))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissFunc() },
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
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
    onPriorityChange: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Compartment(stringResource(R.string.title_description), content = {
            // Ввод названия
            OutlinedTextField(
                value = title,
                onValueChange = { newText -> onTitleChange(newText) },
                placeholder = { Text(text = stringResource(R.string.enter_title)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16))
            )

            // Ввод описания
            OutlinedTextField(
                value = description,
                onValueChange = { newText -> onDescriptionChange(newText) },
                placeholder = { Text(text = stringResource(R.string.enter_description)) },
                modifier = Modifier
                    .fillMaxWidth()

            )
        })


        val priorities = listOf(Priority.Easy, Priority.Medium, Priority.Hard)
        Compartment(stringResource(R.string.priority), content = {
            priorities.forEach { priority ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = priority == selectedOpinion,
                        onClick = { onPriorityChange(priority) },
                        colors = RadioButtonColors(
                            selectedColor = getColorByPriority(priority),
                            unselectedColor = MaterialTheme.colorScheme.onBackground,
                            disabledSelectedColor = MaterialTheme.colorScheme.onBackground,
                            disabledUnselectedColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(text = getPriorityText(priority), fontSize = 18.sp)
                }
            }
        })


        // Выбор категории
        Compartment(stringResource(R.string.category_required), content = {
            CategoryHorizontalSelector(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    onCategoryChange(category)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)

            )
        })
    }

}

@Composable
private fun CategoryHorizontalSelector(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = Category.entries.filter { it != Category.Unknown }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.forEach { category ->
            Card(
                modifier = Modifier
                    .width(120.dp)
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (category == selectedCategory) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
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
                    )
                }
            }
        }
    }
}

@Composable
private fun getPriorityText(priority: Priority): String {
    return when (priority) {
        is Priority.Easy -> stringResource(R.string.easy_priority)
        is Priority.Medium -> stringResource(R.string.medium_priority)
        is Priority.Hard -> stringResource(R.string.hard_priority)
    }
}


@Composable
private fun Compartment(
    title: String,
    content: @Composable () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    modifier: Modifier = Modifier
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
        // Заголовок
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // Контент
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
        is Priority.Easy -> Color(0xFF0ABAB5)
        is Priority.Medium -> Color(0xFFCC7722)
        is Priority.Hard -> Color(0xFFDC143C)
    }
}

