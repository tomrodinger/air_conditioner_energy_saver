package com.app.imotion.model

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime

/**
 * Created by hani.fakhouri on 2023-06-05.
 */
sealed interface TriggerData {

    data class MotionDetection(
        val sensitivity: Int
    ) : TriggerData

    data class NoMotion(
        val sensitivity: Int,
        val durationSeconds: Long,
        val days: List<DayOfWeek>,
    ) : TriggerData

    data class ScheduledTime(
        val time: LocalTime,
        val days: List<DayOfWeek>,
    ) : TriggerData

    data class ScheduledTimeWindowWithMotionDetection(
        val sensitivity: Int,
        val startTime: LocalTime,
        val endTime: LocalTime,
        val days: List<DayOfWeek>,
    ) : TriggerData

}