package com.hyvu.alarmclock.utils

import java.util.*

class Utils {
    companion object {
        fun toDay(day: Int): String {
            when (day) {
                Calendar.SUNDAY -> return "Sunday"
                Calendar.MONDAY -> return "Monday"
                Calendar.TUESDAY -> return "Tuesday"
                Calendar.WEDNESDAY -> return "Wednesday"
                Calendar.THURSDAY -> return "Thursday"
                Calendar.FRIDAY -> return "Friday"
                Calendar.SATURDAY -> return "Saturday"
            }
            throw Exception("Could not locate day")
        }
    }
}