package com.sas.companymanagement.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.databinding.FragmentMainBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.artist.ArtistCategory
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.schedule.ScheduleHorizontalAdapter

/**
 * Main Tab
 *
 * @fileName             : MainFragment
 * @auther               : 이기영
 * @since                : 2023-10-17
 **/
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


    /**
     * main view recycler 처리
     *
     * @param view
     * @param savedInstanceState
     * @author 이기영
     */
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

            // artist, schedule 을 모두 가져와 recycler view 에 등록
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
        onBackPressed()
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

    /**
     * On back pressed
     * 뒤로가기 버튼 눌렀을 때 처리
     * @author 윤성욱
     */
    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }

}