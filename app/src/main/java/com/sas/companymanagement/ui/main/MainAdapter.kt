package com.sas.companymanagement.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.ItemMainArtistBinding
import com.sas.companymanagement.databinding.ItemScheduleHorizontalBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistFragmentDirections
import com.sas.companymanagement.ui.schedule.Schedule
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class MainAdapter(
    private var artistList: MutableList<Artist>,
    private var scheduleList: MutableList<Schedule>,
    private val fragment: Fragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ArtistHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView = itemView.findViewById<ImageView>(R.id.iv_artist)
    }

    inner class ScheduleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var names: TextView = itemView.findViewById<TextView>(R.id.schedule_date)
        var dates: TextView = itemView.findViewById<TextView>(R.id.schedule_name)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                ArtistHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_main_artist,
                        parent,
                        false
                    )
                )
            }

            else -> {
                ScheduleHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_schedule_horizontal,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val artistData = artistList[position]
//        val scheduleData = scheduleList[position]

        if (holder is ArtistHolder) {
//            holder.images = artistData.artistImage
            artistClickEvent(holder.images, artistData)
        }
    }


    private fun artistClickEvent(view: View, artist: Artist) {
        view.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                findNavController(fragment).navigate(
                    MainFragmentDirections.actionFragmentMainToArtistDetailFragment(
                        artist.id.toInt()
                    )
                )
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArtistList(artists: List<Artist>) {
        artistList = artists.toMutableList()
        // 현재 스케쥴 데이터를 가져올 수 없어 임시로 데이터 저장
        scheduleList = mutableListOf<Schedule>(
            Schedule(
                scheduleName = "임시네임",
                scheduleAddress = "임시주소",
                scheduleContent = "임시내용",
                scheduleDateAfter = "임시 before",
                scheduleDateBefore = "임시 after"
            )
        )
        notifyDataSetChanged()
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