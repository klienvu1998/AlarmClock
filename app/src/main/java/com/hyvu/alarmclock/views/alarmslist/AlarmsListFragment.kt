package com.hyvu.alarmclock.views.alarmslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyvu.alarmclock.R
import com.hyvu.alarmclock.adapters.AlarmListAdapter
import com.hyvu.alarmclock.databinding.FragmentAlarmsListBinding
import com.hyvu.alarmclock.models.Alarm
import com.hyvu.alarmclock.viewmodels.AlarmListViewModel
import com.hyvu.alarmclock.viewmodels.MainActivityViewModel
import com.hyvu.alarmclock.views.createalarmdialog.CreateAlarmDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmsListFragment : Fragment() {

    companion object {
        const val CREATE_ALARM_DIALOG = "create-alarm-dialog"
    }

    private lateinit var mBinding: FragmentAlarmsListBinding
    private val mViewModelSharedMain: MainActivityViewModel by activityViewModels()
    private val mViewModelAlarmList: AlarmListViewModel by viewModels()
    private val mAdapter by lazy { AlarmListAdapter(::onRemoveAlarm) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_alarms_list, container, false)
        mBinding = FragmentAlarmsListBinding.bind(v)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun onRemoveAlarm(alarm: Alarm) {
        mViewModelAlarmList.removeAlarm(alarm.alarmId)
    }

    private fun initData() {
        mViewModelAlarmList.getAlarms().observe(viewLifecycleOwner) {
            val newList = ArrayList(it)
            mAdapter.submitList(newList)
        }
    }

    private fun initView() {
        mBinding.recyclerViewAlarm.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mBinding.recyclerViewAlarm.adapter = mAdapter
        mBinding.btnCreateAlarm.setOnClickListener {
            showCreateAlarmDialog()
        }
    }

    private fun showCreateAlarmDialog() {
        val ft = parentFragmentManager.beginTransaction()
        val existDialog = parentFragmentManager.findFragmentByTag(CREATE_ALARM_DIALOG)
        if (existDialog != null) {
            ft.remove(existDialog)
        }
        val createAlarmDialog = CreateAlarmDialogFragment()
        createAlarmDialog.show(parentFragmentManager.beginTransaction(), CREATE_ALARM_DIALOG)
    }

}