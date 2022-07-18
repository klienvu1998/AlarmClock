package com.hyvu.alarmclock.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyvu.alarmclock.models.Alarm
import com.hyvu.alarmclock.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val alarmRepository: AlarmRepository): ViewModel() {

    private val compositeDispose by lazy { CompositeDisposable() }

    private val _isComplete: MutableLiveData<Boolean> = MutableLiveData(false)
    val isComplete: LiveData<Boolean> = _isComplete

    fun insertAlarm(alarm: Alarm) {
        compositeDispose.add(
            alarmRepository.insertAlarm(alarm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _isComplete.postValue(true)
                }, {

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDispose.clear()
    }

}