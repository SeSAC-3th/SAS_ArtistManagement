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
import com.sas.companymanagement.databinding.RecyclerviewArtistItemBinding
import com.sas.companymanagement.databinding.RecyclerviewScheduleItemBinding
import kotlin.math.roundToInt

data class ArtistRV(val artistImage: Int = 0)

class MainArtistRecyclerViewAdapter(
    private val artistList: MutableList<ArtistRV>,
    private val owner: Fragment
) : RecyclerView.Adapter<MainArtistRecyclerViewAdapter.ArtistHolder>() {
    inner class ArtistHolder(val binding: RecyclerviewArtistItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistHolder {
        val binding =
            RecyclerviewArtistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ArtistHolder(binding)

    }

    override fun onBindViewHolder(holder: ArtistHolder, position: Int) {
        val artistData = artistList[position]

        with(holder.binding) {
            artistImage.setImageResource(artistData.artistImage)

            root.setOnClickListener {
            }
        }
    }
    override fun getItemCount() = artistList.size
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