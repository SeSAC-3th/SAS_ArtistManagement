package com.sas.companymanagement.ui.group

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.ui.artist.ArtistFragmentDirections

class GroupAdapter(var context: Context, var arrayList: ArrayList<Group>, var fragment: Fragment) :
    RecyclerView.Adapter<GroupAdapter.ItemHolder>() {

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images = itemView.findViewById<ImageView>(R.id.recyclerImage)
        var artistNames = itemView.findViewById<TextView>(R.id.recyclerName)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_layout, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var group: Group = arrayList.get(position)
//        holder.images.setImageResource(group.groupImage!!)
        holder.artistNames.text = group.groupName

        groupClickEvent(holder.images)
    }

    private fun groupClickEvent(view: View) {
        val observable = view.clicks()
        observable.subscribe {
            val action =
                GroupFragmentDirections.actionFragmentGroupToGroupDetailFragment(0)
            NavHostFragment.findNavController(fragment).navigate(action)
        }
    }

}