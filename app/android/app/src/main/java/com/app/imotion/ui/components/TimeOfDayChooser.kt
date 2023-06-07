package com.app.imotion.ui.components

import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.imotion.R
import kotlinx.datetime.LocalTime
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * Created by hani.fakhouri on 2023-06-07.
 */

@Composable
fun TimeOfDayChooser(
    time: LocalTime,
    onTimeUpdated: (LocalTime) -> Unit,
) {
    val context = LocalContext.current
    var showNativeTimePicker by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showNativeTimePicker = true
            },
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ValueController(
                icon = R.drawable.timer_minus,
                pressSensitivity = ValueControllerPressSensitivity.HIGH,
                onClick = {
                    onTimeUpdated(time.abstractOneMinute())
                },
            )
            HorizontalSpacer(space = 8.dp)

            Row {
                TimeComponent(time.hour)
                Colon()
                TimeComponent(time.minute)
            }

            HorizontalSpacer(space = 8.dp)
            ValueController(
                icon = R.drawable.timer_plus,
                pressSensitivity = ValueControllerPressSensitivity.HIGH,
                onClick = {
                    onTimeUpdated(time.addOneMinute())
                }
            )
        }

        Image(
            modifier = Modifier
                .size(32.dp)
                .clickable { showNativeTimePicker = true }
                .align(Alignment.CenterEnd),
            painter = painterResource(R.drawable.baseline_access_time),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
            contentDescription = null
        )
    }
    LaunchedEffect(showNativeTimePicker) {
        if (showNativeTimePicker) {
            val dialog = TimePickerDialog(
                context,
                { _, hour: Int, minute: Int ->
                    onTimeUpdated(LocalTime(hour = hour, minute = minute))
                },
                time.hour, time.minute, false
            )
            dialog.setOnDismissListener {
                showNativeTimePicker = false
            }
            dialog.show()
        }
    }
}

private fun LocalTime.addOneMinute(): LocalTime {
    val newSeconds = this.toSecondOfDay() + 1.minutes.inWholeSeconds.toInt()
    return if (newSeconds > 24.hours.inWholeSeconds) {
        LocalTime(0, 0)
    } else {
        LocalTime.fromMillisecondOfDay(newSeconds.seconds.inWholeMilliseconds.toInt())
    }
}

private fun LocalTime.abstractOneMinute(): LocalTime {
    val newSeconds = this.toSecondOfDay() - 1.minutes.inWholeSeconds.toInt()
    return if (newSeconds < 0) {
        LocalTime(hour = 23, minute = 59)
    } else {
        LocalTime.fromMillisecondOfDay(newSeconds.seconds.inWholeMilliseconds.toInt())
    }
}