package com.sas.companymanagement.ui.group.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.sas.companymanagement.R
import com.sas.companymanagement.R.menu.menu_detail
import com.sas.companymanagement.databinding.FragmentGroupDetailBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment


class GroupDetailFragment :
    ViewBindingBaseFragment<FragmentGroupDetailBinding>(FragmentGroupDetailBinding::inflate) {


    companion object {
        fun newInstance() = GroupDetailFragment()
    }

    val SCORE_START = 0

    private var artistRecyclerView: RecyclerView? = null
    private val viewModel: GroupDetailViewModel by viewModels()
    private var artistAdapter = ArtistAdapter(mutableListOf(), this)
    private val groupArgs: GroupDetailFragmentArgs by navArgs()
    private var artistIds = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingArtistRecyclerView()
        fieldSetUp()
        listenerSetup()
    }


    private fun settingArtistRecyclerView() {
        with(binding) {
            artistRecyclerView = rvArtist
            artistAdapter = ArtistAdapter(ArrayList(), requireParentFragment())
            artistRecyclerView?.layoutManager =
                GridLayoutManager(
                    context,
                    2,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            artistRecyclerView?.setHasFixedSize(true)
            artistRecyclerView?.adapter = artistAdapter
        }
    }


    private fun listenerSetup() {
        binding.tbGroup.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.tbGroup.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.update -> {
                    findNavController().navigate(
                        GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupUpdateFragment(
                            groupArgs.groupId
                        )
                    )
                }

                R.id.delete -> {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage(R.string.delete_message)
                        .setPositiveButton(resources.getString(R.string.yes)) { dialog, id ->
                            viewModel.deleteGroup(groupArgs.groupId)
                            findNavController().popBackStack()
                        }
                        .setNegativeButton(resources.getString(R.string.no)) { dialog, id ->
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


    private fun fieldSetUp() {
        val id = groupArgs.groupId
        viewModel.findGroup(id)
        viewModel.getSearchResults().observe(viewLifecycleOwner) { group ->
            with(binding) {
                tbGroup.title = group.groupName
                iv.setImageURI(group.groupImage.toUri())
                iv.setBackgroundColor(Color.TRANSPARENT)
                artistIds = group.artistId
                setPieChart(group.groupEval)
            }
            getArtistData()
        }
    }

    private fun getArtistData() {
        val newArtists = mutableListOf<Artist>()
        viewModel.getAllArtist()?.observe(viewLifecycleOwner) { artists ->
            artists.forEach { artist ->
                artistIds.split(", ").forEach { id ->
                    try {
                        if (id.toLong() == artist.id) {
                            newArtists.add(artist)
                        }
                    } catch (e: NumberFormatException) {
                    }
                }
            }
            artistAdapter.setArtistList(newArtists)
        }
    }

    private fun setPieChart(inputData: String) {
        val pieChart = binding.pieGroupChart
        pieChart.setUsePercentValues(true)

        val entries = ArrayList<PieEntry>()
        for (index in SCORE_START until inputData.length) { // 수정된 부분
            entries.add(
                PieEntry(
                    inputData[index].toString().toFloat(),
                    resources.getStringArray(R.array.score_list).getOrNull(index) ?: ""
                )
            ) // 수정된 부분
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
            centerText = resources.getString(R.string.eval_score)
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInQuad)
            animate()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}