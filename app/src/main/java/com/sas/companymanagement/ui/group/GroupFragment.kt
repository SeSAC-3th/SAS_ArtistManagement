package com.sas.companymanagement.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter

class GroupFragment : Fragment() {
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    private var groupRecyclerView : RecyclerView? = null
    private var groupGridLayoutManager : GridLayoutManager? = null
    private var groupList : ArrayList<Group>? = null
    private var groupAdapter : GroupAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            groupRecyclerView = groupRV
            groupGridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            groupRecyclerView?.layoutManager = groupGridLayoutManager
            groupRecyclerView?.setHasFixedSize(true)
            groupList = ArrayList()
            groupList = setDatainList()
            groupAdapter = GroupAdapter(requireContext(),groupList!!)
            groupRecyclerView?.adapter = groupAdapter
        }

    }

    private fun setDatainList() : ArrayList<Group>{

        var items: ArrayList<Group> = ArrayList()

        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))
        items.add(Group(R.drawable.ic_add_circle_24,"김채원"))

        return items
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}