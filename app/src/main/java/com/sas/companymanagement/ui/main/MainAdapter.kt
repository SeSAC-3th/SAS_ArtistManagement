package com.sas.companymanagement.ui.main

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.databinding.ItemMainArtistBinding
import com.sas.companymanagement.ui.artist.ArtistFragmentDirections
import kotlin.math.roundToInt

data class ArtistRV(val artistImage: Int = 0)

class MainAdapter(
    private val artistList: MutableList<ArtistRV>,
    private val owner: Fragment
) : RecyclerView.Adapter<MainAdapter.ArtistHolder>() {
    inner class ArtistHolder(val binding: ItemMainArtistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistHolder {
        val binding =
            ItemMainArtistBinding.inflate(
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
            artistClickEvent(this.artistImage)
        }
    }

    private fun artistClickEvent(view: View) {
        val observable = view.clicks()
        observable.subscribe {
            val action =
                MainFragmentDirections.actionFragmentMainToArtistDetailFragment(0)
            NavHostFragment.findNavController(owner).navigate(action)
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