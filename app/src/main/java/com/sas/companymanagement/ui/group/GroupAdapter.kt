package com.sas.companymanagement.ui.group

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R


class GroupAdapter(
    var arrayList: MutableList<Group>,
    var fragment: Fragment
) :
    RecyclerView.Adapter<GroupAdapter.ItemHolder>() {

    /**
     * RecycleView 아이템 설정
     *
     * @param itemView RecyclerVivew의 아이템 뷰
     */
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images = itemView.findViewById<ImageView>(R.id.recyclerImage)
        var groupNames = itemView.findViewById<TextView>(R.id.recyclerName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_layout, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val groupData: Group = arrayList[position]
        holder.groupNames.text = groupData.groupName
        holder.images.setImageURI(groupData.groupImage.toUri())
        groupClickEvent(holder.images, groupData)
    }

    @SuppressLint("CheckResult")
    private fun groupClickEvent(view: View, group: Group) {
        val observable = view.clicks()
        observable.subscribe {
            val action =
                GroupFragmentDirections.actionFragmentGroupToGroupDetailFragment(group.id)
            NavHostFragment.findNavController(fragment).navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setGroupList(groups: List<Group>) {
        arrayList = groups.toMutableList()
        notifyDataSetChanged()
    }

}