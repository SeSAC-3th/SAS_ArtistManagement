package com.sas.companymanagement.ui.artist.detail

import android.app.AlertDialog
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistDetailBinding
import com.sas.companymanagement.ui.common.CANCEL
import com.sas.companymanagement.ui.common.DELETE_MESSAGE
import com.sas.companymanagement.ui.common.EVAL_SCORE
import com.sas.companymanagement.ui.common.OK
import com.sas.companymanagement.ui.common.SCORE_LIST
import com.sas.companymanagement.ui.common.SCORE_SIZE
import com.sas.companymanagement.ui.common.SCORE_START
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.common.dateToString
import com.sas.companymanagement.ui.schedule.Schedule
import com.sas.companymanagement.ui.schedule.ScheduleHorizontalAdapter
import com.sas.companymanagement.ui.schedule.ScheduleViewModel

class ArtistDetailFragment :
    ViewBindingBaseFragment<FragmentArtistDetailBinding>(FragmentArtistDetailBinding::inflate) {

    companion object {
        fun newInstance() = ArtistDetailFragment()
    }

    private val viewModel: ArtistDetailViewModel by viewModels()
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private val artistArgs: ArtistDetailFragmentArgs by navArgs()
    private var scheduleRecyclerView: RecyclerView? = null
    private var scheduleAdapter = ScheduleHorizontalAdapter(mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scheduleSetup()
        getScheduleData()
        fieldSetup()
        listenerSetup()
    }

    private fun getScheduleData() {
        val newSchedules = mutableListOf<Schedule>()
        scheduleViewModel.allSchedules?.observe(viewLifecycleOwner) { schedules ->
            schedules.forEach { schedule ->
                schedule.artistId.split(", ").forEach { id ->
                    if (id == artistArgs.artistId.toString()) {
                        newSchedules.add(schedule)
                    }
                }
            }
            scheduleAdapter.setScheduleList(newSchedules.toList())
        }
    }

    private fun scheduleSetup() {
        with(binding) {
            scheduleRecyclerView = rvSchedule
            scheduleAdapter =
                ScheduleHorizontalAdapter(ArrayList(), requireParentFragment())
            scheduleRecyclerView?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            scheduleRecyclerView?.setHasFixedSize(true)
            scheduleRecyclerView?.adapter = scheduleAdapter
        }
    }

    private fun fieldSetup() {
        val id = artistArgs.artistId
        viewModel.findArtist(id)
        viewModel.getSearchResults().observe(viewLifecycleOwner) { artist ->
            with(binding) {
                tvArtistNameLayout.text = artist.artistName
                tvArtistNicknameLayout.text = artist.artistNickname
                iv.setImageURI(Uri.parse(artist.artistImage))
                iv.setBackgroundColor(Color.TRANSPARENT)
                tvArtistBirthLayout.text = dateToString(artist.artistBirth)
                tvArtistJobLayout.text = artist.artistCategory
                tvArtistGenderLayout.text = artist.artistGender
                setPieChart(artist.artistEval)
            }
        }
    }


    private fun listenerSetup() {
        with(binding.tvArtist) {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.update -> {
                        findNavController().navigate(
                            ArtistDetailFragmentDirections.actionArtistDetailFragmentToArtistUpdateFragment(
                                artistArgs.artistId
                            )
                        )
                    }

                    R.id.delete -> {
                        val ad = AlertDialog.Builder(this.context)
                        ad.setIcon(R.drawable.ic_launcher_foreground)
                        ad.setTitle(DELETE_MESSAGE)

                        ad.setNegativeButton(CANCEL) { dialog, _ ->
                            dialog.dismiss()
                        }
                        ad.setPositiveButton(OK) { dialog, _ ->
                            dialog.dismiss()
                            viewModel.deleteArtist(artistArgs.artistId)
                            findNavController().popBackStack()
                        }
                        ad.show()
                    }
                }
                true
            }
        }
    }

    /**
     * Set pie chart
     * mpChart
     * referrencelink : https://goodgoodminki.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%9B%90%ED%98%95-%EA%B7%B8%EB%9E%98%ED%94%84-%ED%8C%8C%EC%9D%B4-%EA%B7%B8%EB%9E%98%ED%94%84-Android-Koltin-Circle-Graph-Pie-Graph
     */
    private fun setPieChart(inputData: String) {
        val pieChart = binding.pieChart
        pieChart.setUsePercentValues(true)

        val entries = ArrayList<PieEntry>()
        for (index in SCORE_START..SCORE_SIZE) {
            entries.add(PieEntry(inputData[index].toString().toFloat(), SCORE_LIST[index]))
        }

        val colorItems = ArrayList<Int>()
        ColorTemplate.VORDIPLOM_COLORS.forEach { colorItems.add(it) }
        ColorTemplate.JOYFUL_COLORS.forEach { colorItems.add(it) }
        ColorTemplate.LIBERTY_COLORS.forEach { colorItems.add(it) }
        ColorTemplate.PASTEL_COLORS.forEach { colorItems.add(it) }
        colorItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(entries, "").apply {
            colors = colorItems
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }

        val pieData = PieData(pieDataSet)
        pieChart.apply {
            data = pieData
            description.isEnabled = false
            isRotationEnabled = false
            centerText = EVAL_SCORE
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInQuad)
            animate()
        }
    }
}