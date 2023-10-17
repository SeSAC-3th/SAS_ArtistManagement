package com.sas.companymanagement.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)


        with(binding.rvSchedule) {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
//            addItemDecoration(ScheduleItem(context, 10f,35f, Color.CYAN,20f))
            adapter = MainScheduleRecyclerViewAdapter(scheduleData(), this@MainFragment)
        }

        with(binding.rvSinger) {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MainArtistRecyclerViewAdapter(singerData(), this@MainFragment)
        }

        with(binding.rvActor) {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MainArtistRecyclerViewAdapter(actorData(), this@MainFragment)
        }

        with(binding.rvTalent) {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MainArtistRecyclerViewAdapter(talentData(), this@MainFragment)
        }

        return binding.root
    }

    private fun scheduleData() = mutableListOf<ScheduleRV>().apply {
        add(ScheduleRV("2023-10-15", "테스트1"))
        add(ScheduleRV("2023-10-16", "테스트2"))
        add(ScheduleRV("2023-10-17", "테스트3"))
    }

    private fun singerData() = mutableListOf<ArtistRV>().apply {
        add(ArtistRV(R.drawable.dummy_image))
        add(ArtistRV(R.drawable.dummy_image))
        add(ArtistRV(R.drawable.dummy_image))
    }

    private fun actorData() = mutableListOf<ArtistRV>().apply {
        add(ArtistRV(R.drawable.dummy_image))
        add(ArtistRV(R.drawable.dummy_image))
        add(ArtistRV(R.drawable.dummy_image))
    }

    private fun talentData() = mutableListOf<ArtistRV>().apply {
        add(ArtistRV(R.drawable.dummy_image))
        add(ArtistRV(R.drawable.dummy_image))
        add(ArtistRV(R.drawable.dummy_image))
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}