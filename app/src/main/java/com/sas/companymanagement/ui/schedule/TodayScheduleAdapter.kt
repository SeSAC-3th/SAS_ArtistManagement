package com.sas.companymanagement.ui.schedule

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import androidx.navigation.fragment.findNavController
import com.sas.companymanagement.databinding.FragmentArtistBinding
import java.util.concurrent.TimeUnit

data class TodaySchedule(val content: String)
class TodayScheduleAdapter(
    private val navigateScheduleDetail: (Int) -> Unit,
) :
    RecyclerView.Adapter<TodayScheduleAdapter.ItemHolder>() {

    var arrayList: MutableList<TodaySchedule> = mutableListOf()

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content = itemView.findViewById<TextView>(R.id.tv_schedule_info)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var schedule: TodaySchedule = arrayList[position]
        holder.content.text = schedule.content

        artistClickEvent(holder.content)

    }

    @SuppressLint("CheckResult")
    private fun artistClickEvent(view: View) {
        view.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                Log.e("navigate","button click")
                navigateScheduleDetail(0)
//                val action =
//                    ScheduleFragmentDirections.actionFragmentScheduleToScheduleDetailFragment(0)
//                findNavController(fragment).navigate(action)
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setScheduleList(schedules: MutableList<TodaySchedule>) {
        arrayList = schedules
        notifyDataSetChanged()
    }
}