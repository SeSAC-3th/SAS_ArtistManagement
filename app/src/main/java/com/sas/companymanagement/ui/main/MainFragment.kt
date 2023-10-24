package com.sas.companymanagement.ui.main

import android.annotation.SuppressLint
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
import com.sas.companymanagement.ui.artist.ArtistCategory
import com.sas.companymanagement.ui.artist.ArtistViewModel
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.schedule.Schedule
import com.sas.companymanagement.ui.schedule.ScheduleAdapter
import com.sas.companymanagement.ui.schedule.ScheduleHorizontalAdapter
import java.util.ArrayList

class MainFragment :
    ViewBindingBaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()
    private var singerAdapter = ArtistAdapter(mutableListOf(), this)
    private var actorAdapter = ArtistAdapter(mutableListOf(), this)
    private var talentAdapter = ArtistAdapter(mutableListOf(), this)

    private var singerRecyclerView: RecyclerView? = null
    private var actorRecyclerView: RecyclerView? = null
    private var talentRecyclerView: RecyclerView? = null

    private var scheduleRecyclerView: RecyclerView? = null
    private var scheduleAdapter = ScheduleHorizontalAdapter(mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            scheduleRecyclerView = rvSchedule
            scheduleAdapter = ScheduleHorizontalAdapter(ArrayList(), requireParentFragment())
            scheduleRecyclerView?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            scheduleRecyclerView?.setHasFixedSize(true)
            scheduleRecyclerView?.adapter = scheduleAdapter

            singerRecyclerView = rvSinger
            singerAdapter = ArtistAdapter(ArrayList(), requireParentFragment())
            setRecyclerView(singerRecyclerView!!, singerAdapter)

            actorRecyclerView = rvActor
            actorAdapter = ArtistAdapter(ArrayList(), requireParentFragment())
            setRecyclerView(actorRecyclerView!!, actorAdapter)

            talentRecyclerView = rvTalent
            talentAdapter = ArtistAdapter(ArrayList(), requireParentFragment())
            setRecyclerView(talentRecyclerView!!, talentAdapter)

            viewModel.allSchedules?.observe(viewLifecycleOwner) { schedules ->
                scheduleAdapter.setScheduleList(schedules)
            }
            viewModel.allArtists?.observe(viewLifecycleOwner) { artists ->
                val singers = arrayListOf<Artist>()
                val talents = arrayListOf<Artist>()
                val actors = arrayListOf<Artist>()
                artists.forEach { artist ->
                    when (artist.artistCategory) {
                        ArtistCategory.SINGER.job -> singers.add(artist)
                        ArtistCategory.TALENT.job -> talents.add(artist)
                        else -> actors.add(artist)
                    }
                }
                singerAdapter.setArtistList(singers)
                talentAdapter.setArtistList(talents)
                actorAdapter.setArtistList(actors)
            }
        }
    }

    private fun setRecyclerView(recyclerView: RecyclerView, adapter: ArtistAdapter) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}