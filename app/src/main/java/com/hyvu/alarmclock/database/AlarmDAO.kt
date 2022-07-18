package com.hyvu.alarmclock.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hyvu.alarmclock.models.Alarm
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface AlarmDAO {
    @Insert
    fun insert(alarm: Alarm): Completable

    @Query("SELECT * FROM alarm_table ORDER BY created ASC")
    fun getAlarms(): LiveData<List<Alarm>>

    @Update
    fun update(alarm: Alarm)

    @Query("DELETE FROM alarm_table WHERE alarmId = :alarmId")
    fun removeAlarm(alarmId: Long): Completable
}