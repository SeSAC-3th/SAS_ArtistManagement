package com.sas.companymanagement.ui.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment

class GroupFragment :
    ViewBindingBaseFragment<FragmentGroupBinding>(FragmentGroupBinding::inflate) {

    private var groupRecyclerView: RecyclerView? = null
    private var groupGridLayoutManager: GridLayoutManager? = null
    private var groupList: ArrayList<Group>? = null
    private var groupAdapter = GroupAdapter(mutableListOf(), this)
    private val viewModel: GroupViewModel by viewModels()


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

        with(binding) {

            settingGroupRecyclerView()
            tbGroup.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.btnAdd) {
                    val action =
                        GroupFragmentDirections.actionFragmentGroupToGroupUpdateFragment(-1)
                    findNavController().navigate(action)

                }
                true
            }
        }

        viewModel.allGroup?.observe(viewLifecycleOwner) { groups ->
            groupAdapter.setGroupList(groups)
        }
    }

    private fun settingGroupRecyclerView() {
        with(binding) {
            groupRecyclerView = rvGroup
            groupGridLayoutManager =
                GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            groupRecyclerView?.layoutManager = groupGridLayoutManager
            groupRecyclerView?.setHasFixedSize(true)
            groupList = ArrayList()
            groupAdapter = GroupAdapter(groupList!!, requireParentFragment())
            groupRecyclerView?.adapter = groupAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}