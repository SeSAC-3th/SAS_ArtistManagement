package com.sas.companymanagement.ui.group.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sas.companymanagement.R

class GroupUpdateFragment : Fragment() {

    companion object {
        fun newInstance() = GroupUpdateFragment()
    }

    private lateinit var viewModel: GroupUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_update, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}