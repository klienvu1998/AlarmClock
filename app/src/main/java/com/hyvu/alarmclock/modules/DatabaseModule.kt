package com.hyvu.alarmclock.modules

import android.content.Context
import androidx.room.Room
import com.hyvu.alarmclock.database.AlarmDAO
import com.hyvu.alarmclock.database.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAlarmDao(database: AlarmDatabase): AlarmDAO {
        return database.alarmDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AlarmDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AlarmDatabase::class.java,
            "alarm.db"
        ).build()
    }

}