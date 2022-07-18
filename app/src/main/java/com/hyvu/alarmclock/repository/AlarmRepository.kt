package com.hyvu.alarmclock.repository

import androidx.lifecycle.LiveData
import com.hyvu.alarmclock.models.Alarm
import io.reactivex.Completable
import io.reactivex.Single

interface AlarmRepository {
    fun getAlarms(): LiveData<List<Alarm>>
    fun insertAlarm(alarm: Alarm): Completable
    fun removeAlarm(alarmId: Long): Completable
}