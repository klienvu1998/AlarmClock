package com.hyvu.alarmclock.views.createalarmdialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.hyvu.alarmclock.R
import com.hyvu.alarmclock.databinding.FragmentCreateAlarmBinding
import com.hyvu.alarmclock.models.Alarm
import com.hyvu.alarmclock.models.WeekDays
import com.hyvu.alarmclock.viewmodels.MainActivityViewModel

class CreateAlarmDialogFragment : DialogFragment() {

    private lateinit var mBinding: FragmentCreateAlarmBinding
    private val mViewModelSharedMain: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_create_alarm, container, false)
        mBinding = FragmentCreateAlarmBinding.bind(v)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mBinding.btnCancel.setOnClickListener {
            dismiss()
        }

        mBinding.btnOk.setOnClickListener {
            val weekDays = with(mBinding.layoutRepeatDay) {
                WeekDays(
                    toggleBtnMonday.isChecked,
                    toggleBtnTuesday.isChecked,
                    toggleBtnWednesday.isChecked,
                    toggleBtnThursday.isChecked,
                    toggleBtnFriday.isChecked,
                    toggleBtnSaturday.isChecked,
                    toggleBtnSunday.isChecked,
                )
            }

            val alarm = Alarm(
                alarmId = System.currentTimeMillis(),
                hour = mBinding.timePicker.hour,
                minute = mBinding.timePicker.minute,
                weekDays = weekDays,
                started = true,
                recurring = mBinding.layoutRepeatDay.swRecurring.isChecked,
                created = System.currentTimeMillis()
            )
            alarm.schedule(requireContext())
            mViewModelSharedMain.insertAlarm(alarm)
            dismiss()
        }
    }
}