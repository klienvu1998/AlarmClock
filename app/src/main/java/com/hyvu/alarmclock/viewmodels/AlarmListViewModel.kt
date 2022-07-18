package com.hyvu.alarmclock.viewmodels

import androidx.lifecycle.ViewModel
import com.hyvu.alarmclock.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AlarmListViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
): ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun getAlarms() = alarmRepository.getAlarms()

    fun removeAlarm(alarmId: Long) {
        compositeDisposable.add(
            alarmRepository.removeAlarm(alarmId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

}