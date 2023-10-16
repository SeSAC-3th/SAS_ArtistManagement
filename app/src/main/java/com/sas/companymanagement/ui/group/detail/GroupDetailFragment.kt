package com.sas.companymanagement.ui.group.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupDetailBinding
import com.sas.companymanagement.databinding.FragmentMainBinding
import com.sas.companymanagement.ui.MainActivity
import com.sas.companymanagement.ui.main.MainArtistRecyclerViewAdapter
import com.sas.companymanagement.ui.main.MainFragment
import com.sas.companymanagement.ui.main.MainScheduleRecyclerViewAdapter
import com.sas.companymanagement.ui.main.ScheduleRV


class GroupDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GroupDetailFragment()
    }

    private lateinit var viewModel: GroupDetailViewModel
    private var _binding: FragmentGroupDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailBinding.inflate(inflater, container, false)


        with(binding.scheduleRV) {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
//            addItemDecoration(ScheduleItem(context, 10f,35f, Color.CYAN,20f))
            adapter = GroupScheduleRecyclerViewAdapter(scheduleData(), this@GroupDetailFragment)
        }

        with(binding.artistRV) {
            layoutManager = GridLayoutManager(this.context, 2).apply {
                canScrollVertically()
            }
//            addItemDecoration(ScheduleItem(context, 10f,35f, Color.CYAN,20f))
            adapter = GroupArtistRecyclerViewAdapter(artistData(), this@GroupDetailFragment)
        }
        return binding.root

    }

    private fun scheduleData() = mutableListOf<GroupScheduleRV>().apply {
        add(GroupScheduleRV("2023-10-15", "테스트1"))
        add(GroupScheduleRV("2023-10-16", "테스트2"))
        add(GroupScheduleRV("2023-10-17", "테스트3"))
    }

    private fun artistData() = mutableListOf<GroupArtistRV>().apply {
        add(GroupArtistRV(R.drawable.dummy_image, "테스트1"))
        add(GroupArtistRV(R.drawable.dummy_image, "테스트2"))
        add(GroupArtistRV(R.drawable.dummy_image, "테스트3"))
        add(GroupArtistRV(R.drawable.dummy_image, "테스트4"))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}