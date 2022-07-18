package com.hyvu.alarmclock.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyvu.alarmclock.R
import com.hyvu.alarmclock.databinding.ItemAlarmBinding
import com.hyvu.alarmclock.models.Alarm
import javax.inject.Inject

class AlarmListAdapter @Inject constructor(val onRemoveAlarm: (Alarm) -> Unit): ListAdapter<Alarm, AlarmListAdapter.ViewHolder>(AlarmDiffCallback()) {

    private var context: Context? = null

    class AlarmDiffCallback: DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.alarmId == newItem.alarmId
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val mBinding: ItemAlarmBinding = ItemAlarmBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun onBind(alarm: Alarm) {
            mBinding.cbToggleAlarm.isChecked = alarm.started
            if (alarm.hour < 12) {
                mBinding.layoutTime.tvDayOrNight.text = "AM"
                mBinding.layoutTime.tvTime.text = "${alarm.hour}:${alarm.minute}"
            } else {
                mBinding.layoutTime.tvDayOrNight.text = "PM"
                mBinding.layoutTime.tvTime.text = "${alarm.hour - 12}:${alarm.minute}"
            }
            mBinding.tvDate.text = "4 May"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_alarm, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        holder.mBinding.btnRemove.setOnClickListener {
            onRemoveAlarm(item)
        }
    }

}