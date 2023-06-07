package com.app.imotion.model

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.imotion.ui.components.DayOfWeekRoundedButton

/**
 * Created by hani.fakhouri on 2023-06-07.
 */

@Composable
fun DaysOfWeekChooser(
    selectedDays: Set<DayOfWeekLabel>,
    onDaySelected: (DayOfWeekLabel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(state = rememberScrollState(), enabled = true),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AllDaysOfWeek.forEach {
            val isSelected = selectedDays.contains(it)
            DayOfWeekRoundedButton(
                day = it.short,
                isSelected = isSelected,
                onSelected = {
                    onDaySelected(it)
                }
            )
        }
    }
}