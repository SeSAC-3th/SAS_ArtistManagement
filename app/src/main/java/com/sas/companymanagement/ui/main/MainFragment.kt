package com.sas.companymanagement.ui.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sas.companymanagement.databinding.FragmentMainBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.schedule.update.ScheduleUpdateFragment
import com.sas.companymanagement.ui.schedule.update.ScheduleUpdateViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainFragment : ViewBindingBaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    companion object {
        fun newInstance() = ScheduleUpdateFragment()
    }

    private lateinit var viewModel: ScheduleUpdateViewModel
    private val compositeDisposable = CompositeDisposable()

    val manager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainList = mutableListOf(
            MainDataClass("2023.10.4","르세라핌","뮤직뱅크"),
            MainDataClass("2023.10.15","르핌","뮤크")
        )


        with(binding.mainRv){
            layoutManager = manager
            adapter = MainRecyclerViewAdapter(mainList,requireActivity())
        }
    }

    override fun onDestroyView() {
        _binding = null
        compositeDisposable.clear()
        super.onDestroyView()
    }



}