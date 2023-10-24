package com.sas.companymanagement.ui.schedule

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.R

class ScheduleAdapter(var context: Context, var arrayList: MutableList<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var scheduleNames = itemView.findViewById<TextView>(R.id.tv_schedule_info)
        /**
         *  xml 상에서 date 부분이 없음
        var scheduleDates = itemView.findViewById<TextView>(R.id.tv_schedule_date)
         **/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var schedule: Schedule = arrayList.get(position)

        holder.scheduleNames.text = schedule.scheduleName

        /**
         *
         *         holder.scheduleDates.text = schedule.scheduleDateBefore
         *
         *         holder.scheduleDates.text = schedule.scheduleDate
         *
         * **/

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setScheduleList(schedules: List<Schedule>) {
        arrayList = schedules.toMutableList()

        notifyDataSetChanged()
    }
}