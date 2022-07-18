package com.hyvu.alarmclock.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.hyvu.alarmclock.services.AlarmService
import java.util.*

class AlarmBroadcastReceiver: BroadcastReceiver() {
    companion object {
        val MONDAY = "MONDAY"
        val TUESDAY = "TUESDAY"
        val WEDNESDAY = "WEDNESDAY"
        val THURSDAY = "THURSDAY"
        val FRIDAY = "FRIDAY"
        val SATURDAY = "SATURDAY"
        val SUNDAY = "SUNDAY"
        val RECURRING = "RECURRING"
        val TITLE = "TITLE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            Toast.makeText(context, "Alarm reboot", Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context)
        } else {
            Toast.makeText(context, "Alarming", Toast.LENGTH_SHORT).show()
            if (intent?.getBooleanExtra(RECURRING, false) == false) {
                startAlarmService(context, intent)
            } else {
                if (intent != null) {
                    if (alarmIsToday(intent)) {
                        startAlarmService(context, intent)
                    }
                }
            }
        }
    }

    private fun alarmIsToday(intent: Intent): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today = calendar.get(Calendar.DAY_OF_WEEK)

        when (today) {
            Calendar.MONDAY -> {
                return intent.getBooleanExtra(MONDAY, false)
            }
            Calendar.TUESDAY -> {
                return intent.getBooleanExtra(TUESDAY, false)
            }
            Calendar.WEDNESDAY -> {
                return intent.getBooleanExtra(WEDNESDAY, false)
            }
            Calendar.THURSDAY -> {
                return intent.getBooleanExtra(THURSDAY, false)
            }
            Calendar.FRIDAY -> {
                return intent.getBooleanExtra(FRIDAY, false)
            }
            Calendar.SATURDAY -> {
                return intent.getBooleanExtra(SATURDAY, false)
            }
            Calendar.SUNDAY -> {
                return intent.getBooleanExtra(SUNDAY, false)
            }
        }
        return false
    }

    private fun startAlarmService(context: Context?, intent: Intent) {
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(intentService)
        } else {
            context?.startService(intentService)
        }
    }

    private fun startRescheduleAlarmsService(context: Context?) {
        TODO("Not yet implemented")
    }
}