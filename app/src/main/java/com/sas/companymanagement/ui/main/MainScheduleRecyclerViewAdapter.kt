package com.sas.companymanagement.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.databinding.RecyclerviewScheduleItemBinding
import kotlin.math.roundToInt

data class ScheduleRV(val calendarDate: String = "", var calendarName: String = "")

class MainScheduleRecyclerViewAdapter(
    private val scheduleList: MutableList<ScheduleRV>,
    private val owner: Fragment
) : RecyclerView.Adapter<MainScheduleRecyclerViewAdapter.ScheduleHolder>() {
    inner class ScheduleHolder(val binding: RecyclerviewScheduleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHolder {
        val binding =
            RecyclerviewScheduleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ScheduleHolder(binding)

    }

    override fun onBindViewHolder(holder: ScheduleHolder, position: Int) {
        val scheduleData = scheduleList[position]

        with(holder.binding) {
            scheduleDate.text = scheduleData.calendarDate
            scheduleName.text = scheduleData.calendarName
            root.setOnClickListener {
            }
        }
    }
    override fun getItemCount() = scheduleList.size
}

class CalendarItemDecoration(
    private val context: Context,
    private val height: Float,
    private var bottomPadding: Float = 50f,
) : RecyclerView.ItemDecoration() {


    init {
        bottomPadding = dpToPx(50f)
    }

    // dp -> pixel 단위로 변경
    private fun dpToPx(dp: Float) =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            context.resources.displayMetrics
        )
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = bottomPadding.roundToInt()
    }
}