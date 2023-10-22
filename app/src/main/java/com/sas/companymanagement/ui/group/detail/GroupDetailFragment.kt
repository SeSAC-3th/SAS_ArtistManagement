package com.sas.companymanagement.ui.group.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sas.companymanagement.R
import com.sas.companymanagement.R.menu.menu_detail
import com.sas.companymanagement.R.menu.menu_update
import com.sas.companymanagement.databinding.FragmentGroupDetailBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.schedule.Schedule
import com.sas.companymanagement.ui.schedule.ScheduleAdapter


class GroupDetailFragment :
    ViewBindingBaseFragment<FragmentGroupDetailBinding>(FragmentGroupDetailBinding::inflate) {


    companion object {
        fun newInstance() = GroupDetailFragment()
    }

    private lateinit var viewModel: GroupDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailBinding.inflate(inflater, container, false)


        with(binding.rvSchedule) {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
//            addItemDecoration(ScheduleItem(context, 10f,35f, Color.CYAN,20f))
            adapter = ScheduleAdapter(this.context, scheduleData())
        }

        with(binding.rvArtist) {
            layoutManager = GridLayoutManager(this.context, 2).apply {
                canScrollVertically()
            }
//            addItemDecoration(ScheduleItem(context, 10f,35f, Color.CYAN,20f))
            adapter = ArtistAdapter(artistData(), requireParentFragment())
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerSetup()
    }

    private fun listenerSetup() {
        binding.tbGroup.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.update -> {
                    val tb = binding.tbGroup
                    tb.menu.clear()
                    tb.inflateMenu(menu_update)
                }

                R.id.delete -> {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("삭제하시겠습니까?")
                        .setPositiveButton("Yes") { dialog, id ->

                        }
                        .setNegativeButton("No") { dialog, id ->

                        }

                    val alertDialog = builder.create()
                    alertDialog.show()
                }

                R.id.menu_update -> {
                        val tb = binding.tbGroup
                        tb.menu.clear()
                        tb.inflateMenu(menu_detail)
                }

            }

            true

        }
    }

    private fun scheduleData() = mutableListOf<Schedule>().apply {
        add(Schedule("2023-10-15", "테스트1"))
        add(Schedule("2023-10-16", "테스트2"))
        add(Schedule("2023-10-17", "테스트3"))
    }

    private fun artistData() = mutableListOf<Artist>().apply {
//        add(Artist(R.drawable.dummy_image, "테스트1"))
//        add(Artist(R.drawable.dummy_image, "테스트2"))
//        add(Artist(R.drawable.dummy_image, "테스트3"))
//        add(Artist(R.drawable.dummy_image, "테스트4"))
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