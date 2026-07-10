package com.example.myapplication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun WeeklyStrip(dates: List<Int>) {
    val dayOfWeek = listOf<String>(
        stringResource(R.string.monday),
        stringResource(R.string.tuesday),
        stringResource(R.string.wednesday),
        stringResource(R.string.thursday),
        stringResource(R.string.friday),
        stringResource(R.string.saturday),
        stringResource(R.string.sunday),
    )

    val dailyInfo = dates.zip(dayOfWeek)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .height(110.dp)
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(12))
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(12)
            )
            .padding(10.dp)
    ) {
        dailyInfo.forEach { data ->
            DayOfTheWeek( // Реализуй, что день активен
                dayNum = data.first.toString(),
                weekDay = data.second,
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = {/*Функция нажатия на конкретный день*/ })
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWeeklyStrip() {
    WeeklyStrip((1..7).toList())
}