package com.sas.companymanagement.ui.artist

import android.annotation.SuppressLint
import android.content.Context
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
import com.sas.companymanagement.ui.schedule.Schedule
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

class ArtistAdapter(
    var arrayList: MutableList<Artist>,
    var fragment: Fragment
) :
    RecyclerView.Adapter<ArtistAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images = itemView.findViewById<ImageView>(R.id.recyclerImage)
        var names = itemView.findViewById<TextView>(R.id.recyclerName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_layout, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val artist: Artist = arrayList.get(position)
//        holder.images.setImageResource(artist.artistImage!!)
        holder.names.text = artist.artistName

        artistClickEvent(holder.images)
    }

    private fun artistClickEvent(view: View) {
        view.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                val action =
                    ArtistFragmentDirections.actionFragmentArtistToArtistDetailFragment(0)
                findNavController(fragment).navigate(action)
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArtistList(artists: List<Artist>) {
        arrayList = artists.toMutableList()
        notifyDataSetChanged()
    }
}