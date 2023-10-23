package com.sas.companymanagement.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupDetailBinding
import com.sas.companymanagement.databinding.FragmentMainBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.artist.ArtistViewModel
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.schedule.Schedule
import com.sas.companymanagement.ui.schedule.ScheduleAdapter

class MainFragment :
    ViewBindingBaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()
    private var mainAdapter = MainAdapter(mutableListOf(), mutableListOf(), this)

    private var artistList: MutableList<Artist>? = null
    private var singerRecyclerView: RecyclerView? = null
    private var actorRecyclerView: RecyclerView? = null
    private var talentRecyclerView: RecyclerView? = null

    private var scheduleList: MutableList<Schedule>? = null
    private var scheduleRecyclerView: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            artistList = ArrayList()
            scheduleList = ArrayList()
            mainAdapter = MainAdapter(
                artistList!!,
                scheduleList!!, requireParentFragment()
            )

            singerRecyclerView = rvSinger
            createByCategory(singerRecyclerView!!)

            actorRecyclerView = rvActor
            createByCategory(actorRecyclerView!!)

            talentRecyclerView = rvTalent
            createByCategory(talentRecyclerView!!)

//            rvSchedule.layoutManager =
//                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            addItemDecoration(ScheduleItem(context, 10f, 35f, Color.CYAN, 20f))
        }

        viewModel.allArtists?.observe(viewLifecycleOwner) { artists ->
            Log.e("mainInfo", "$artists")
            mainAdapter.setArtistList(artists)
        }

    }

    private fun createByCategory(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(false)
        recyclerView.adapter = mainAdapter
    }

    private fun scheduleData() = mutableListOf<Schedule>().apply {
        add(Schedule("2023-10-15", "테스트1"))
        add(Schedule("2023-10-16", "테스트2"))
        add(Schedule("2023-10-17", "테스트3"))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}