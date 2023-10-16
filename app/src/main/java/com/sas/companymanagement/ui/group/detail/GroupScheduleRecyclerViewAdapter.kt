package com.sas.companymanagement.ui.group.detail

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
import com.sas.companymanagement.databinding.RecyclerviewArtistItemBinding
import com.sas.companymanagement.databinding.RecyclerviewScheduleItemBinding
import com.sas.companymanagement.ui.main.ScheduleRV
import kotlin.math.roundToInt

data class GroupScheduleRV(val groupDate: String = "", val groupName: String = "")


class GroupScheduleRecyclerViewAdapter(
    private val scheduleList: MutableList<GroupScheduleRV>,
    private val owner: Fragment
) : RecyclerView.Adapter<GroupScheduleRecyclerViewAdapter.GroupHolder>() {
    inner class GroupHolder(val binding: RecyclerviewScheduleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val binding =
            RecyclerviewScheduleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GroupHolder(binding)

    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        val scheduleData = scheduleList[position]

        with(holder.binding) {
            scheduleDate.text = scheduleData.groupDate
            scheduleName.text = scheduleData.groupName
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