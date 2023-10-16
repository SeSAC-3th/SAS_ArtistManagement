package com.sas.companymanagement.ui.group.detail

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.databinding.RecyclerviewArtistItemBinding
import com.sas.companymanagement.databinding.RecyclerviewGroupArtistItemBinding
import com.sas.companymanagement.databinding.RecyclerviewScheduleItemBinding
import kotlin.math.roundToInt

data class GroupArtistRV(val artistImage: Int, val artistName: String = "")


class GroupArtistRecyclerViewAdapter(
    private val scheduleList: MutableList<GroupArtistRV>,
    private val owner: Fragment
) : RecyclerView.Adapter<GroupArtistRecyclerViewAdapter.GroupHolder>() {
    inner class GroupHolder(val binding: RecyclerviewGroupArtistItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val binding =
            RecyclerviewGroupArtistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GroupHolder(binding)

    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        val artistData = scheduleList[position]

        with(holder.binding) {
            artistImage.setImageResource(artistData.artistImage)
            artistName.text = artistData.artistName
            root.setOnClickListener {
            }
        }
    }
    override fun getItemCount() = scheduleList.size
}

class ArtistItemDecoration(
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