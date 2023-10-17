package com.sas.companymanagement.ui.schedule

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.R

class ScheduleAdapter(var context: Context, var arrayList: MutableList<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var scheduleNames = itemView.findViewById<TextView>(R.id.schedule_name)
        var scheduleDates = itemView.findViewById<TextView>(R.id.schedule_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule_horizontal, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var schedule: Schedule = arrayList.get(position)
        Log.e("position", "${position}")
        Log.e("arrayList", "${arrayList.get(0)}")
        Log.e("size", "${getItemCount()}")
        Log.e("name", "${schedule.scheduleName}")
        holder.scheduleNames.text = schedule.scheduleName
        holder.scheduleDates.text = schedule.scheduleDate
    }
}