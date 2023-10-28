package com.sas.companymanagement.ui.schedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.ui.artist.ArtistFragment
import com.sas.companymanagement.ui.artist.ArtistFragmentDirections
import com.sas.companymanagement.ui.artist.detail.ArtistDetailFragmentDirections
import com.sas.companymanagement.ui.main.MainFragment
import com.sas.companymanagement.ui.main.MainFragmentDirections
import java.util.concurrent.TimeUnit
import kotlin.math.min

/**
 * MainFragment 와 ArtistDetail 에서 보여줄 schedule
 *
 * @fileName             : ScheduleHorizontalAdapter
 * @auther               : 이기영
 * @since                : 2023-10-24
 **/
class ScheduleHorizontalAdapter(
    private var arrayList: MutableList<Schedule>,
    private var fragment: Fragment
) :
    RecyclerView.Adapter<ScheduleHorizontalAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var scheduleNames: TextView = itemView.findViewById(R.id.tv_schedule_name)
        var scheduleDates: TextView = itemView.findViewById(R.id.tv_schedule_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule_horizontal, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val scheduleData: Schedule = arrayList.get(position)

        holder.scheduleNames.text = scheduleData.scheduleName
        holder.scheduleDates.text = scheduleData.scheduleDateAfter

        scheduleClickEvent(holder.scheduleDates, scheduleData)
        scheduleClickEvent(holder.scheduleNames, scheduleData)
    }

    private fun scheduleClickEvent(view: View, schedule: Schedule) {
        val action =
            if (fragment.childFragmentManager.fragments[0] is MainFragment) {
                MainFragmentDirections.actionFragmentMainToScheduleDetailFragment(schedule.id)
            } else {
                ArtistDetailFragmentDirections.actionArtistDetailFragmentToScheduleDetailFragment(
                    schedule.id
                )
            }
        with(view) {
            clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    NavHostFragment.findNavController(fragment).navigate(action)
                }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setScheduleList(schedules: List<Schedule>) {
        val maxSize = min(schedules.size, 3)
        arrayList = schedules.toMutableList().subList(0, maxSize)
        notifyDataSetChanged()
    }
}