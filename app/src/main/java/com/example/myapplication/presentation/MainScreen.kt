package com.example.myapplication.presentation

import android.R.attr.textSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.presentation.components.WeeklyStrip
import com.example.myapplication.R
import com.example.myapplication.domain.Category
import com.example.myapplication.domain.Priority
import com.example.myapplication.domain.Task
import com.example.myapplication.presentation.components.AddTask
import com.example.myapplication.presentation.components.TaskCard
import java.time.LocalDate

@Composable
fun MainScreen(viewModel: MainViewModel) {
    LaunchedEffect(Unit) {
        val curDate = LocalDate.now()
        val day: Int = curDate.dayOfMonth
        val month: Int = curDate.month.value
        val year: Int = curDate.year
        viewModel.loadTaskByDate(String.format("%02d.%02d.%04d", day, month, year))
    }
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val curDate by viewModel.date.collectAsStateWithLifecycle()
    val isAdd by viewModel.isOpenAddScreen.collectAsStateWithLifecycle()

    if (isAdd) {
        AddTask(viewModel::closeAddScreen) { task ->
            viewModel.addTask(task)
        }
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 40.dp, start = 5.dp, end = 5.dp)
    ) {
        item {
            Title(
                curDate = curDate,
                funcSetData = viewModel::setDate, viewModel::openAddScreen
            )
        }
        if (tasks.isEmpty()) {
            item {

                Text(
                    text = stringResource(R.string.no_task_provided),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(100.dp)
                        .clip(RoundedCornerShape(16))
                        .background(MaterialTheme.colorScheme.onSecondary)
                        .wrapContentSize(Alignment.Center)


                )
            }
        } else {
            items(items = tasks, key = { task -> task.title }) { task ->
                val stateSwitch = rememberSaveable { mutableStateOf(task.isComplete) }
                // Изменить здесь архитектуру
                TaskCard(task)
            }
        }


    }
}

@Composable
private fun Title(
    curDate: LocalDate,
    funcSetData: (LocalDate) -> Unit,
    btnAdd: () -> Unit = {}
) {
    Column {
        // Вывод заголовка секции(Календарь)
        Text(
            text = stringResource(R.string.calendar),
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
        )
        // Недельная строка
        WeeklyStrip(curDate, funcSetData)

        // Список дел + добавить
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text( // Список дел
                text = stringResource(R.string.todo_list),
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )


            Text(
                text = "+",
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                modifier = Modifier
                    .width(60.dp)
                    .height(40.dp)
                    .padding(end = 10.dp, top = 10.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(30)
                    )
                    .clickable(onClick = { btnAdd })

            )

        }

    }
}


@Preview
@Composable
private fun ShowMainScreen() {
    MainScreen(hiltViewModel())
}