package com.sas.companymanagement.ui.artist

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import com.sas.companymanagement.databinding.FragmentArtistBinding
import com.sas.companymanagement.ui.MainActivity
import com.sas.companymanagement.ui.group.GroupFragment
import com.sas.companymanagement.ui.group.GroupFragmentDirections
import com.sas.companymanagement.ui.group.detail.GroupDetailFragment
import com.sas.companymanagement.ui.group.detail.GroupDetailFragmentDirections
import com.sas.companymanagement.ui.main.MainFragmentDirections
import java.util.concurrent.TimeUnit

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
                selectedSet.forEach {
                    Log.e("selectID", it.toString())
                }
            }
    }

    fun getSelectedId(): MutableSet<Long> {
        return selectedSet
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArtistList(artists: List<Artist>) {
        Log.e("artistInfo", "2번")

        arrayList = artists.toMutableList()
        notifyDataSetChanged()
    }
}