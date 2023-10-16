package com.sas.companymanagement.ui.artist.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.sas.companymanagement.databinding.FragmentArtistDetailBinding
import com.sas.companymanagement.ui.group.detail.GroupScheduleRV
import com.sas.companymanagement.ui.group.detail.GroupScheduleRecyclerViewAdapter

class ArtistDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ArtistDetailFragment()
    }

    private lateinit var viewModel: ArtistDetailViewModel
    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        setPieChart()
        with(binding.scheduleRV) {
            layoutManager = LinearLayoutManager(this.context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
//            addItemDecoration(ScheduleItem(context, 10f,35f, Color.CYAN,20f))
            adapter = GroupScheduleRecyclerViewAdapter(scheduleData(), this@ArtistDetailFragment)
        }

        return binding.root
    }
    private fun scheduleData() = mutableListOf<GroupScheduleRV>().apply {
        add(GroupScheduleRV("2023-10-15", "테스트1"))
        add(GroupScheduleRV("2023-10-16", "테스트2"))
        add(GroupScheduleRV("2023-10-17", "테스트3"))
    }

    /**
     * Set pie chart
     * mpChart
     * referrencelink : https://goodgoodminki.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%9B%90%ED%98%95-%EA%B7%B8%EB%9E%98%ED%94%84-%ED%8C%8C%EC%9D%B4-%EA%B7%B8%EB%9E%98%ED%94%84-Android-Koltin-Circle-Graph-Pie-Graph
     */
    private fun setPieChart() {
        val pieChart = binding.pieChart
        pieChart.setUsePercentValues(true)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(508f, "Apple"))
        entries.add(PieEntry(600f, "Orange"))
        entries.add(PieEntry(750f, "Mango"))
        entries.add(PieEntry(508f, "Grapes"))
        entries.add(PieEntry(670f, "Banana"))

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
            centerText = "level"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInQuad)
            animate()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtistDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}