package com.sas.companymanagement.ui.schedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import java.util.concurrent.TimeUnit
/**
 * @fileName             : TodayScheduleAdapter
 * @auther               : 박지혜
 * @since                : 2023-10-18
 **/
class TodayScheduleAdapter(
    private val navigateScheduleDetail: (Long) -> Unit,
) :
    RecyclerView.Adapter<TodayScheduleAdapter.ItemHolder>() {

    private var arrayList: MutableList<Schedule> = mutableListOf()

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content: TextView = itemView.findViewById<TextView>(R.id.tv_schedule_info)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val schedule: Schedule = arrayList[position]
        holder.content.text =
            schedule.scheduleContent + " (" + schedule.scheduleDateBefore.substring(13) + "~" + schedule.scheduleDateAfter.substring(
                13
            ) + ")"

        artistClickEvent(holder.content, schedule.id)

    }

    @SuppressLint("CheckResult")
    private fun artistClickEvent(view: View, scheduleId: Long) {
        view.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                navigateScheduleDetail(scheduleId)
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setScheduleList(schedules: List<Schedule>?) {
        if (schedules != null) {
            arrayList = schedules.toMutableList()
        }
        notifyDataSetChanged()
    }
}