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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sas.companymanagement.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}*/
