package com.sas.companymanagement.ui.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistBinding
import com.sas.companymanagement.databinding.FragmentGroupBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment

class GroupFragment : ViewBindingBaseFragment<FragmentGroupBinding>(FragmentGroupBinding::inflate) {

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
        with(binding){
            groupRecyclerView = rvGroup
            groupGridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            groupRecyclerView?.layoutManager = groupGridLayoutManager
            groupRecyclerView?.setHasFixedSize(true)
            groupList = ArrayList()
            groupList = setDataInList()
            groupAdapter = GroupAdapter(requireContext(),groupList!!)
            groupRecyclerView?.adapter = groupAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setDataInList() : ArrayList<Group>{

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