package com.sas.companymanagement.ui.main

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.ui.artist.Artist
import java.util.concurrent.TimeUnit

class MainAdapter(
    private var artistList: MutableList<Artist>,
    private val fragment: Fragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ArtistHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView = itemView.findViewById<ImageView>(R.id.iv_artist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArtistHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_main_artist,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val artistData = artistList[position]

        if (holder is ArtistHolder) {
            holder.images.setImageURI(Uri.parse(artistData.artistImage))
            artistClickEvent(holder.images, artistData)
        }
    }


    private fun artistClickEvent(view: View, artist: Artist) {
        view.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                findNavController(fragment).navigate(
                    MainFragmentDirections.actionFragmentMainToArtistDetailFragment(
                        artist.id
                    )
                )
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArtistList(artists: List<Artist>) {
        artistList = artists.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = artistList.size
}