package com.sas.companymanagement.ui.group

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.R

class GroupAdapter(var context: Context, var arrayList: ArrayList<Group>): RecyclerView.Adapter<GroupAdapter.ItemHolder>() {

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var images = itemView.findViewById<ImageView>(R.id.recyclerImage)
        var artistNames = itemView.findViewById<TextView>(R.id.recyclerName)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_layout_list_item, parent,false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var group : Group = arrayList.get(position)
        holder.images.setImageResource(group.groupImage!!)
        holder.artistNames.text = group.groupName

        holder.images.setOnClickListener {
            Toast.makeText(context,group.groupName, Toast.LENGTH_SHORT).show()
        }

    }

}