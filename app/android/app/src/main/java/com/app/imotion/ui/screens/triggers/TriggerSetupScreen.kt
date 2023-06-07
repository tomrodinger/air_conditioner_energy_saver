package com.app.imotion.ui.screens.triggers

import android.icu.util.TimeZone
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.R
import com.app.imotion.model.*
import com.app.imotion.ui.components.*
import com.app.imotion.ui.theme.PreviewTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDateTime
import java.lang.Integer.max

/**
 * Created by hani.fakhouri on 2023-06-05.
 */
@Composable
fun TriggerSetupScreen(
    irCodes: List<IrCode>,
    onCreateTrigger: (TriggerData) -> Unit,
    onBack: () -> Unit,
) {
    SimpleScreenTemplate(
        title = "Add Control Rules",
        onBack = onBack,
        content = {
            TriggerSetupUi(
                irCodes = irCodes,
                onCreateTrigger = onCreateTrigger,
            )
        }
    )
}

@Composable
private fun TriggerSetupUi(
    irCodes: List<IrCode>,
    onCreateTrigger: (TriggerData) -> Unit,
) {
    var selectedIrCode by remember { mutableStateOf<IrCode?>(null) }
    var selectedTrigger by remember { mutableStateOf<TriggerType?>(null) }
    var enableAddTriggerButton by remember { mutableStateOf(false) }
    var triggerData by remember { mutableStateOf<TriggerData?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0F)
                .verticalScroll(rememberScrollState(), enabled = true)
        ) {
            DropDownSelector(
                values = irCodes,
                currentValue = selectedIrCode,
                valueStringProvider = { it.readableName },
                noSelectionText = "Select IR Code",
                onSelected = { selectedIrCode = it }
            )
            VerticalSpacer(space = 16.dp)
            DropDownSelector(
                values = listOf(
                    TriggerType.OnMotionDetection,
                    TriggerType.OnNoMotion,
                    TriggerType.OnScheduledTime,
                    TriggerType.OnMotionScheduleTimeWindow,
                ),
                currentValue = selectedTrigger,
                valueStringProvider = {
                    when (it) {
                        TriggerType.OnMotionDetection -> "Trigger if motion is detected"
                        TriggerType.OnNoMotion -> "Trigger if motion stops"
                        TriggerType.OnScheduledTime -> "Trigger at a scheduled time"
                        TriggerType.OnMotionScheduleTimeWindow -> "Trigger if motion is detected within a time window"
                    }
                },
                noSelectionText = "Select Trigger",
                onSelected = { selectedTrigger = it }
            )

            when (selectedTrigger) {
                TriggerType.OnMotionDetection -> {
                    var sensitivityValue by remember { mutableStateOf(0F) }
                    MotionSensitivitySlider(
                        currentValue = sensitivityValue,
                        onValueChanged = { sensitivityValue = it }
                    )
                    enableAddTriggerButton = true
                    triggerData =
                        TriggerData.MotionDetection(sensitivity = sensitivityValue.toInt())
                }
                TriggerType.OnNoMotion -> {
                    var sensitivityValue by remember { mutableStateOf(0F) }
                    var durationSeconds by remember { mutableStateOf(0) }
                    var selectedDays by remember { mutableStateOf(mutableSetOf<DayOfWeekLabel>()) }
                    NoMotionSetupUi(
                        currentSensitivityValue = sensitivityValue,
                        durationSeconds = durationSeconds,
                        selectedDays = selectedDays,
                        onSensitivityValueChanged = { sensitivityValue = it },
                        onDurationSecondsUpdated = { durationSeconds = it },
                        onDaySelected = {
                            val copy = mutableSetOf<DayOfWeekLabel>().apply {
                                addAll(selectedDays)
                                if (this.contains(it)) {
                                    this.remove(it)
                                } else {
                                    this.add(it)
                                }
                            }
                            selectedDays = mutableSetOf<DayOfWeekLabel>().apply {
                                addAll(copy)
                            }
                        }
                    )
                    enableAddTriggerButton = durationSeconds > 0 && selectedDays.isNotEmpty()
                    triggerData = TriggerData.NoMotion(
                        sensitivity = sensitivityValue.toInt(),
                        durationSeconds = durationSeconds.toLong(),
                        days = selectedDays.map { it.dayOfWeek },
                    )
                }
                TriggerType.OnScheduledTime -> {
                    var time by remember {
                        mutableStateOf(
                            Clock.System.now().toLocalDateTime(
                                kotlinx.datetime.TimeZone.of(TimeZone.getDefault().id)
                            ).time
                        )
                    }
                    var selectedDays by remember { mutableStateOf(mutableSetOf<DayOfWeekLabel>()) }
                    ScheduledTimeUi(
                        time = time,
                        selectedDays = selectedDays,
                        onTimeUpdated = { time = it },
                        onDaySelected = {
                            val copy = mutableSetOf<DayOfWeekLabel>().apply {
                                addAll(selectedDays)
                                if (this.contains(it)) {
                                    this.remove(it)
                                } else {
                                    this.add(it)
                                }
                            }
                            selectedDays = mutableSetOf<DayOfWeekLabel>().apply {
                                addAll(copy)
                            }
                        }
                    )
                    enableAddTriggerButton = selectedDays.isNotEmpty()
                    triggerData = TriggerData.ScheduledTime(
                        time = time,
                        days = selectedDays.map { it.dayOfWeek },
                    )
                }
                TriggerType.OnMotionScheduleTimeWindow -> {
                    var sensitivityValue by remember { mutableStateOf(0F) }
                    val nowLocalTime = Clock.System.now().toLocalDateTime(
                        kotlinx.datetime.TimeZone.of(TimeZone.getDefault().id)
                    ).time
                    var timeFrom by remember { mutableStateOf(nowLocalTime) }
                    var timeTo by remember { mutableStateOf(nowLocalTime) }
                    var selectedDays by remember { mutableStateOf(mutableSetOf<DayOfWeekLabel>()) }
                    ScheduledTimeWindowUi(
                        sensitivityValue = sensitivityValue,
                        timeFrom = timeFrom,
                        timeTo = timeTo,
                        selectedDays = selectedDays,
                        onSensitivityValueChanged = { sensitivityValue = it },
                        onTimeFromUpdated = { timeFrom = it },
                        onTimeToUpdated = { timeTo = it },
                        onDaySelected = {
                            val copy = mutableSetOf<DayOfWeekLabel>().apply {
                                addAll(selectedDays)
                                if (this.contains(it)) {
                                    this.remove(it)
                                } else {
                                    this.add(it)
                                }
                            }
                            selectedDays = mutableSetOf<DayOfWeekLabel>().apply {
                                addAll(copy)
                            }
                        }
                    )
                    enableAddTriggerButton = selectedDays.isNotEmpty()
                    triggerData = TriggerData.ScheduledTimeWindowWithMotionDetection(
                        sensitivity = sensitivityValue.toInt(),
                        startTime = timeFrom,
                        endTime = timeTo,
                        days = selectedDays.map { it.dayOfWeek },
                    )
                }
                null -> {}
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            if (selectedTrigger != null) {
                VerticalSpacer(space = 24.dp)
                MotionButton(text = "Add this Trigger", enabled = enableAddTriggerButton) {
                    triggerData?.let { triggerData ->
                        onCreateTrigger(triggerData)
                    }
                }
                VerticalSpacer(space = 16.dp)
                MotionButton(text = "Cancel", color = MaterialTheme.colors.onSecondary) {

                }
            }
        }
    }
}

@Composable
private fun ScheduledTimeWindowUi(
    sensitivityValue: Float,
    timeFrom: LocalTime,
    timeTo: LocalTime,
    selectedDays: Set<DayOfWeekLabel>,
    onSensitivityValueChanged: (Float) -> Unit,
    onTimeFromUpdated: (LocalTime) -> Unit,
    onTimeToUpdated: (LocalTime) -> Unit,
    onDaySelected: (DayOfWeekLabel) -> Unit,
) {
    VerticalSpacer(space = 24.dp)
    Column(modifier = Modifier.fillMaxWidth()) {
        MotionSensitivitySlider(
            currentValue = sensitivityValue,
            onValueChanged = onSensitivityValueChanged
        )
        VerticalSpacer(space = 16.dp)

        Text(
            textAlign = TextAlign.Center,
            text = "Set Time (Time is between)",
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W700,
            style = MaterialTheme.typography.body1,
        )
        Divider(modifier = Modifier.padding(vertical = 16.dp))

        TimeOfDayChooser(
            time = timeFrom,
            onTimeUpdated = onTimeFromUpdated
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            text = "To",
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W700,
            style = MaterialTheme.typography.body1,
        )

        TimeOfDayChooser(
            time = timeTo,
            onTimeUpdated = onTimeToUpdated
        )

        VerticalSpacer(space = 16.dp)
        DaysOfWeekChooser(
            selectedDays = selectedDays,
            onDaySelected = onDaySelected,
        )
    }
}

@Composable
private fun ScheduledTimeUi(
    time: LocalTime,
    selectedDays: Set<DayOfWeekLabel>,
    onTimeUpdated: (LocalTime) -> Unit,
    onDaySelected: (DayOfWeekLabel) -> Unit,
) {
    VerticalSpacer(space = 24.dp)
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            textAlign = TextAlign.Center,
            text = "Set Time",
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W700,
            style = MaterialTheme.typography.body1,
        )
        Divider(modifier = Modifier.padding(vertical = 16.dp))

        TimeOfDayChooser(
            time = time,
            onTimeUpdated = onTimeUpdated
        )
        VerticalSpacer(space = 28.dp)


        DaysOfWeekChooser(
            selectedDays = selectedDays,
            onDaySelected = onDaySelected,
        )
    }
}

@Composable
private fun NoMotionSetupUi(
    currentSensitivityValue: Float,
    durationSeconds: Int,
    selectedDays: Set<DayOfWeekLabel>,
    onSensitivityValueChanged: (Float) -> Unit,
    onDurationSecondsUpdated: (Int) -> Unit,
    onDaySelected: (DayOfWeekLabel) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        MotionSensitivitySlider(
            currentValue = currentSensitivityValue,
            onValueChanged = onSensitivityValueChanged,
        )
        VerticalSpacer(space = 36.dp)

        Text(
            textAlign = TextAlign.Center,
            text = "Trigger if no motion for this amount of time",
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W700,
            style = MaterialTheme.typography.body1,
        )
        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val componentHours = durationSeconds / 3600
            val componentMinutes = (durationSeconds % 3600) / 60
            val componentSeconds = durationSeconds % 60

            ValueController(
                icon = R.drawable.timer_minus,
                pressSensitivity = ValueControllerPressSensitivity.HIGH,
                onClick = { onDurationSecondsUpdated(max(0, durationSeconds - 1)) },
            )
            HorizontalSpacer(space = 8.dp)

            Row {
                TimeComponent(componentHours, "hr")
                Colon()
                TimeComponent(componentMinutes, "min")
                Colon()
                TimeComponent(componentSeconds, "sec")
            }

            HorizontalSpacer(space = 8.dp)
            ValueController(
                icon = R.drawable.timer_plus,
                pressSensitivity = ValueControllerPressSensitivity.HIGH,
                onClick = { onDurationSecondsUpdated(durationSeconds + 1) }
            )
        }

        VerticalSpacer(space = 28.dp)

        DaysOfWeekChooser(
            selectedDays = selectedDays,
            onDaySelected = onDaySelected,
        )
    }
}

@Composable
private fun MotionSensitivitySlider(
    currentValue: Float,
    onValueChanged: (Float) -> Unit,
) {
    var value by remember { mutableStateOf(currentValue) }
    val minValue = 0
    val maxValue = 15
    Column(modifier = Modifier.fillMaxWidth()) {
        VerticalSpacer(space = 24.dp)
        Text(
            textAlign = TextAlign.Center,
            text = "Set Sensitivity",
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W700,
            style = MaterialTheme.typography.body1,
        )
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Slider(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0F),
                steps = maxValue,
                valueRange = minValue.toFloat()..maxValue.toFloat(),
                value = value,
                onValueChange = {
                    value = it
                },
                onValueChangeFinished = {
                    onValueChanged(value)
                },
                colors = SliderDefaults.colors(
                    activeTickColor = MaterialTheme.colors.primary,
                    inactiveTickColor = MaterialTheme.colors.secondary.copy(alpha = SliderDefaults.InactiveTrackAlpha),
                )
            )
            Text(
                modifier = Modifier.weight(0.2F),
                textAlign = TextAlign.Center,
                text = "${value.toInt()}",
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.W700,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0F)
                    .padding(horizontal = 8.dp),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    textAlign = TextAlign.Center,
                    text = "$minValue",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    textAlign = TextAlign.Center,
                    text = "$maxValue",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.caption,
                )
            }
            Box(modifier = Modifier.weight(0.2F))
        }
    }

}

@Preview
@Composable
private fun TriggerSetupScreenPreview() {
    PreviewTheme {
        TriggerSetupScreen(
            irCodes = listOf(
                IrCode(readableName = "Turn On"),
                IrCode(readableName = "Turn Off"),
                IrCode(readableName = "Turn On fan"),
            ),
            onCreateTrigger = {},
            onBack = {},
        )
    }
}