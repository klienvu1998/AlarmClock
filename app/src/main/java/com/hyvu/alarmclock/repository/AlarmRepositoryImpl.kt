package com.hyvu.alarmclock.repository

import androidx.lifecycle.LiveData
import com.hyvu.alarmclock.database.AlarmDAO
import com.hyvu.alarmclock.models.Alarm
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDAO
): AlarmRepository {

    override fun getAlarms(): LiveData<List<Alarm>> {
        return alarmDao.getAlarms()
    }

    override fun insertAlarm(alarm: Alarm): Completable {
        return alarmDao.insert(alarm)
    }

    override fun removeAlarm(alarmId: Long): Completable {
        return alarmDao.removeAlarm(alarmId)
    }


}