package com.hyvu.alarmclock.modules

import com.hyvu.alarmclock.database.AlarmDAO
import com.hyvu.alarmclock.repository.AlarmRepository
import com.hyvu.alarmclock.repository.AlarmRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AlarmDataSourceModule {

    @Binds
    abstract fun provideAlarmLocalDataSource(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository

}