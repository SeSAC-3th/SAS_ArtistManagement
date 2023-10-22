package com.sas.companymanagement.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sas.companymanagement.databinding.FragmentScheduleBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.schedule.decorator.ExistedScheduleDayDecorator
import com.sas.companymanagement.ui.schedule.decorator.TodayDecorator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class ScheduleFragment :  ViewBindingBaseFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {

    private val compositeDisposable = CompositeDisposable()
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
                        findNavController().navigate(action)
                    }, {
                        Log.e("RX_ERROR", compositeDisposable.toString())
                    })
            }
        }

        binding.rvScheduleToday.adapter = scheduleAdapter
        binding.cvSchedule.run {
            addDecorator(TodayDecorator())
            setOnDateChangedListener { widget, date, selected ->
                viewModel.getSelectedSchedule(date)
            }
        }

        initObserver()

    }

    private fun initObserver(){
        viewModel.allSchedules?.observe(viewLifecycleOwner) { schedules ->
            setCalendarDays(schedules)
        }

        viewModel.selectedSchedule.observe(viewLifecycleOwner) {
            scheduleAdapter.setScheduleList(it)
        }
    }

    private fun setCalendarDays(schedules: List<Schedule>){
        schedules.forEach{
            val data = it.scheduleDateAfter.substring(0,13)
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
            val date = dateFormat.parse(data)

            val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
            val month = SimpleDateFormat("MM", Locale.getDefault()).format(date)
            val day = SimpleDateFormat("dd", Locale.getDefault()).format(date)

            val addDay: CalendarDay = CalendarDay.from(year.toInt(), month.toInt(), day.toInt())
            binding.cvSchedule.addDecorator(ExistedScheduleDayDecorator(addDay))
        }
    }

    private fun navigateScheduleDetail(id: Int){
        val action = ScheduleFragmentDirections.actionFragmentScheduleToScheduleDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}



//class ScheduleFragment :
//    ViewBindingBaseFragment<FragmentScheduleBinding>(FragmentScheduleBinding::inflate) {
//
//    companion object {
//        fun newInstance() = ScheduleFragment()
//    }
//
//    private lateinit var viewModel: ScheduleUpdateViewModel
//    private val compositeDisposable = CompositeDisposable()
//    private val defaultScope = CoroutineScope(Dispatchers.Default)
//
//
//    @SuppressLint("CheckResult")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ScheduleUpdateViewModel::class.java)
//        with(compositeDisposable) {
//            with(binding) {
//                btnScheduleAdd
//                    .clicks()
//                    .observeOn(Schedulers.io())
//                    .throttleFirst(500, TimeUnit.MILLISECONDS)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                        val action =
//                            ScheduleFragmentDirections.actionFragmentScheduleToScheduleUpdateFragment(0)
//                        findNavController().navigate(action)
//                    }, {
//                        Log.e("RX_ERROR", compositeDisposable.toString())
//                    })
//            }
//            observerSetup()
//            uiSetup()
//        }
//
//
//    }
//
//    private fun observerSetup() {
//        viewModel.getAllSchedules()?.observe(viewLifecycleOwner) { Schedules ->
//            Schedules?.let {
//                scheduleAdapter?.setScheduleList(it)
//            }
//        }
//    }
//    private var scheduleAdapter: ScheduleAdapter? = null
//    private fun uiSetup() {
//        with(binding.rvScheduleToday){
//            scheduleAdapter = ScheduleAdapter(requireContext(), mutableListOf())
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = scheduleAdapter
//        }
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        compositeDisposable.clear()
//    }
//}