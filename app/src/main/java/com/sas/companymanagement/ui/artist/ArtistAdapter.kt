package com.sas.companymanagement.ui.artist

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.ui.group.detail.GroupDetailFragment
import com.sas.companymanagement.ui.group.detail.GroupDetailFragmentDirections
import com.sas.companymanagement.ui.main.MainFragmentDirections
import java.util.concurrent.TimeUnit


/**
 * @fileName             : ArtistAdapter
 * @auther               : 이기영, 윤성욱
 * @since                : 2023-10-18
 **/
class ArtistAdapter(
    private var arrayList: MutableList<Artist>,
    private var fragment: Fragment
) :
    RecyclerView.Adapter<ArtistAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView = itemView.findViewById<ImageView>(R.id.recyclerImage)
        var names: TextView = itemView.findViewById<TextView>(R.id.recyclerName)
    }

    private var selectedSet: MutableSet<Long> = mutableSetOf()
    lateinit var parent: ViewGroup


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_layout, parent, false)
        this.parent = parent
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size


    /**
     * 아티스트 Detail fragment 의 부모 frargment 추적
     *
     * @param holder
     * @param position
     * @author 이기영, 윤성욱
     */
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val artistData: Artist = arrayList[position]
        holder.images.setImageURI(Uri.parse(artistData.artistImage))
        holder.names.text = artistData.artistName
        if (parent.id == R.id.rv_artist_select) {
            artistSelectClickEvent(holder, artistData)
        } else {
            artistClickEvent(holder.images, artistData)
        }
    }

    /**
     * 아티스트 Detail fragment 의 부모 fragment 추적
     *
     * @param view
     * @param artist
     * @author 이기영
     */
    private fun artistClickEvent(view: View, artist: Artist) {
        val action =
            if (fragment.childFragmentManager.fragments[0] is ArtistFragment)
                ArtistFragmentDirections.actionFragmentArtistToArtistDetailFragment(
                    artist.id
                )
            else if (fragment.childFragmentManager.fragments[0] is GroupDetailFragment) {
                GroupDetailFragmentDirections.actionGroupDetailFragmentToArtistDetailFragment(artist.id)
            } else MainFragmentDirections.actionFragmentMainToArtistDetailFragment(artist.id)
        with(view) {
            clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    findNavController(fragment).navigate(
                        action
                    )
                }
        }
    }

    /**
     * ArtistSelect Fragment RecyclerView 클릭 이벤트처리
     *
     * @param holder RecyclerView의 ItemHolder
     * @param artist Artist Id를 얻기 위한 Artist
     * @author 윤성욱
     */
    @SuppressLint("CheckResult")
    private fun artistSelectClickEvent(holder: ItemHolder, artist: Artist) {
        holder.itemView.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                if (artist.id in selectedSet) {
                    selectedSet.remove(artist.id)
                    holder.names.setTextColor(Color.BLACK)
                } else {
                    selectedSet.add(artist.id)
                    holder.names.setTextColor(Color.RED)
                }
            }
    }

    /**
     * 선택한 artistId 가져오는 함수
     *
     * @return 선택한 ID들
     * @author 윤성욱
     */
    fun getSelectedId(): MutableSet<Long> {
        return selectedSet
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArtistList(artists: List<Artist>) {
        arrayList = artists.toMutableList()
        notifyDataSetChanged()
    }
}