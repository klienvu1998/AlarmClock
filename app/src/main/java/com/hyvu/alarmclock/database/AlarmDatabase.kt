package com.hyvu.alarmclock.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyvu.alarmclock.models.Alarm

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDAO

}