package com.sas.companymanagement.ui.schedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentScheduleBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.schedule.update.ScheduleUpdateViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.TimeUnit



class ScheduleFragment :
    ViewBindingBaseFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var viewModel: ScheduleUpdateViewModel
    private val compositeDisposable = CompositeDisposable()
    private val defaultScope = CoroutineScope(Dispatchers.Default)


    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleUpdateViewModel::class.java)
        with(compositeDisposable) {
            with(binding) {
                btnScheduleAdd
                    .clicks()
                    .observeOn(Schedulers.io())
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val action =
                            ScheduleFragmentDirections.actionFragmentScheduleToScheduleUpdateFragment(0)
                        findNavController().navigate(action)
                    }, {
                        Log.e("RX_ERROR", compositeDisposable.toString())
                    })
            }
            observerSetup()
            uiSetup()
        }


    }

    private fun observerSetup() {
        viewModel.getAllSchedules()?.observe(viewLifecycleOwner) { Schedules ->
            Schedules?.let {
                scheduleAdapter?.setScheduleList(it)
            }
        }
    }
    private var scheduleAdapter: ScheduleAdapter? = null
    private fun uiSetup() {
        with(binding.rvScheduleToday){
            scheduleAdapter = ScheduleAdapter(requireContext(), mutableListOf())
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scheduleAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}


/*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistBinding
import com.sas.companymanagement.databinding.FragmentScheduleBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.artist.ArtistFragmentDirections
import com.sas.companymanagement.ui.artist.ArtistViewModel
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ScheduleFragment :  ViewBindingBaseFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {

    private val compositeDisposable = CompositeDisposable()
    private var scheduleList: MutableList<TodaySchedule>? = null
    private var scheduleAdapter = TodayScheduleAdapter(
        navigateScheduleDetail = {scheduleId -> navigateScheduleDetail(scheduleId)}
    )
    private val viewModel: ScheduleViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)

        settingScheduleRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(compositeDisposable) {
            with(binding) {
                btnScheduleAdd
                    .clicks()
                    .observeOn(Schedulers.io())
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                       val action = ScheduleFragmentDirections.actionFragmentScheduleToScheduleUpdateFragment(0)
                        //register면 id가 없는데 이 경우는 어떻게 하지..
                        findNavController().navigate(action)
                    }, {
                        Log.e("RX_ERROR", compositeDisposable.toString())
                    })
            }
        }
    }

    private fun navigateScheduleDetail(id: Int){
        val action = ScheduleFragmentDirections.actionFragmentScheduleToScheduleDetailFragment(id)
        findNavController().navigate(action)
    }


    private fun settingScheduleRecyclerView() {
        with(binding) {
            scheduleList = setDataInList()
            scheduleAdapter.setScheduleList(scheduleList!!)
//            scheduleAdapter = TodayScheduleAdapter(requireContext(), scheduleList!!, requireParentFragment())
            rvScheduleToday.adapter = scheduleAdapter
        }
    }

    private fun setDataInList(): MutableList<TodaySchedule> {

        val items: MutableList<TodaySchedule> = mutableListOf()

        items.add(TodaySchedule("르세라핌 뮤직뱅크 09:00~12:00"))
        items.add(TodaySchedule("르세라핌 뮤직뱅크 09:00~12:00"))
        items.add(TodaySchedule("르세라핌 뮤직뱅크 09:00~12:00"))

//        viewModel.getAllArtists()?.observe(viewLifecycleOwner) { Artists ->
//            Artists.forEach { artist ->
//                items.add(Artist(artist.artistName!!, "src"))
//            }
//        }
        return items
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}*/
