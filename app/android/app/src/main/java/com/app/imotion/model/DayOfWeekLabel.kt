package com.app.imotion.model

import kotlinx.datetime.DayOfWeek

/**
 * Created by hani.fakhouri on 2023-06-06.
 */

enum class DayOfWeekLabel(
    val dayOfWeek: DayOfWeek,
    val short: String,
    val long: String,
) {
    MONDAY(
        dayOfWeek = DayOfWeek.MONDAY,
        short = "Mon",
        long = "Monday",
    ),
    TUESDAY(
        dayOfWeek = DayOfWeek.TUESDAY,
        short = "Tue",
        long = "Tuesday",
    ),
    WEDNESDAY(
        dayOfWeek = DayOfWeek.WEDNESDAY,
        short = "Wed",
        long = "Wednesday",
    ),
    THURSDAY(
        dayOfWeek = DayOfWeek.THURSDAY,
        short = "Thu",
        long = "Thursday",
    ),
    FRIDAY(
        dayOfWeek = DayOfWeek.FRIDAY,
        short = "Fri",
        long = "Friday",
    ),
    SATURDAY(
        dayOfWeek = DayOfWeek.SATURDAY,
        short = "Sat",
        long = "Saturday",
    ),
    SUNDAY(
        dayOfWeek = DayOfWeek.SUNDAY,
        short = "Sun",
        long = "Sunday",
    ),
}

val AllDaysOfWeek = listOf(
    DayOfWeekLabel.MONDAY,
    DayOfWeekLabel.TUESDAY,
    DayOfWeekLabel.WEDNESDAY,
    DayOfWeekLabel.THURSDAY,
    DayOfWeekLabel.FRIDAY,
    DayOfWeekLabel.SATURDAY,
    DayOfWeekLabel.SUNDAY,
)