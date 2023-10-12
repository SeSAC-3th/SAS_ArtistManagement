package com.sas.companymanagement.ui.group.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sas.companymanagement.R

class GroupDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GroupDetailFragment()
    }

    private lateinit var viewModel: GroupDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}