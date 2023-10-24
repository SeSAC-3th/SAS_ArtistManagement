package com.sas.companymanagement.ui.artist

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
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
import com.sas.companymanagement.ui.MainActivity
import com.sas.companymanagement.ui.schedule.Schedule
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ArtistAdapter(
    private var arrayList: MutableList<Artist>,
    private var fragment: Fragment
) :
    RecyclerView.Adapter<ArtistAdapter.ItemHolder>() {

//    private var checkFragment = fragment.childFragmentManager.fragments[0] is ArtistFragment

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView = itemView.findViewById<ImageView>(R.id.recyclerImage)
        var names: TextView = itemView.findViewById<TextView>(R.id.recyclerName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_layout, parent, false)

        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val artistData: Artist = arrayList[position]
        holder.images.setImageURI(Uri.parse(artistData.artistImage))
        holder.names.text = artistData.artistName

        artistClickEvent(holder.images, artistData)
    }

    private fun artistClickEvent(view: View, artist: Artist) {
        view.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                Log.e("artistInfo", "viewId : ${artist.id}")
                val action =
                    ArtistFragmentDirections.actionFragmentArtistToArtistDetailFragment(artist.id.toInt())

                findNavController(fragment).navigate(action)
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArtistList(artists: List<Artist>) {
        arrayList = artists.toMutableList()
        notifyDataSetChanged()
    }
}