package com.hyvu.alarmclock.models

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyvu.alarmclock.broadcasts.AlarmBroadcastReceiver
import com.hyvu.alarmclock.utils.Utils
import java.util.*

@Entity(tableName = "alarm_table")
data class Alarm (
    @PrimaryKey
    @NonNull
    val alarmId: Long,
    val hour: Int,
    val minute: Int,
    var started: Boolean,
    val recurring: Boolean,

    @Embedded
    val weekDays: WeekDays,

    val title: String = "",
    val created: Long
) {
    @SuppressLint("UnspecifiedImmutableFlag")
    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra(AlarmBroadcastReceiver.RECURRING, recurring)
        intent.putExtra(AlarmBroadcastReceiver.MONDAY, weekDays.monday)
        intent.putExtra(AlarmBroadcastReceiver.TUESDAY, weekDays.tuesday)
        intent.putExtra(AlarmBroadcastReceiver.WEDNESDAY, weekDays.wednesday)
        intent.putExtra(AlarmBroadcastReceiver.THURSDAY, weekDays.thursday)
        intent.putExtra(AlarmBroadcastReceiver.FRIDAY, weekDays.friday)
        intent.putExtra(AlarmBroadcastReceiver.SATURDAY, weekDays.saturday)
        intent.putExtra(AlarmBroadcastReceiver.SUNDAY, weekDays.sunday)
        intent.putExtra(AlarmBroadcastReceiver.TITLE, title)

        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // if alarm time has already passed, increase day by 1
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }
        if (!recurring) {
            Toast.makeText(context, String.format(
                "One Time Alarm %s scheduled for %s at %02d:%02d", title, Utils.toDay(
                    calendar[Calendar.DAY_OF_WEEK]
                ), hour, minute, alarmId
            ), Toast.LENGTH_SHORT).show()

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            Toast.makeText(context,
                String.format(
                    "Recurring Alarm %s scheduled for %s at %02d:%02d",
                    title,
                    getRecurringDaysText(),
                    hour,
                    minute,
                    alarmId
                ), Toast.LENGTH_SHORT).show()

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
        this.started = true
    }

    private fun getRecurringDaysText(): String? {
        if (!recurring) {
            return null
        }
        var days = ""
        if (weekDays.monday) {
            days += "Mo "
        }
        if (weekDays.tuesday) {
            days += "Tu "
        }
        if (weekDays.wednesday) {
            days += "We "
        }
        if (weekDays.thursday) {
            days += "Th "
        }
        if (weekDays.friday) {
            days += "Fr "
        }
        if (weekDays.saturday) {
            days += "Sa "
        }
        if (weekDays.sunday) {
            days += "Su "
        }
        return days
    }
}