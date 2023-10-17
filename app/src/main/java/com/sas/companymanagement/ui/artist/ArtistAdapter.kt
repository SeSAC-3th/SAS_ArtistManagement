package com.sas.companymanagement.ui.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.R

class ArtistAdapter(var context: Context, var arrayList: MutableList<Artist>): RecyclerView.Adapter<ArtistAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var images = itemView.findViewById<ImageView>(R.id.recyclerImage)
        var artistNames = itemView.findViewById<TextView>(R.id.recyclerName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_layout, parent,false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

       var artist : Artist = arrayList.get(position)
        holder.images.setImageResource(artist.artistImage!!)
        holder.artistNames.text = artist.artistName

        holder.images.setOnClickListener {
            Toast.makeText(context,artist.artistName,Toast.LENGTH_SHORT).show()
        }

    }

}