package com.sas.companymanagement.ui.schedule.detail

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupDetailBinding
import com.sas.companymanagement.databinding.FragmentScheduleDetailBinding
import com.sas.companymanagement.databinding.FragmentScheduleUpdateBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.artist.ArtistFragmentDirections
import com.sas.companymanagement.ui.artist.detail.ArtistDetailFragmentArgs
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.common.dateToString
import com.sas.companymanagement.ui.schedule.ScheduleAdapter
import com.sas.companymanagement.ui.schedule.ScheduleFragmentDirections
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ScheduleDetailFragment :
    ViewBindingBaseFragment<FragmentScheduleDetailBinding>(FragmentScheduleDetailBinding::inflate) {

    companion object {
        fun newInstance() = ScheduleDetailFragment()
    }
        //TODO 필드 값 변경 
    private val viewModel: ScheduleDetailViewModel by viewModels()
    private val scheduleArgs: ScheduleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        initScheduleDetail()
    }



    private fun initMenu() {
        with(binding.tbScheduleDetail) {
            setOnMenuItemClickListener { item ->
                when ( item.itemId){
                    R.id.update -> {
                        val action =
                            ScheduleDetailFragmentDirections.actionScheduleDetailFragmentToScheduleUpdateFragment(
                                scheduleArgs.scheduleId
                            )
                        findNavController().navigate(action)
                    }
                    R.id.delete -> {
                        val ad = AlertDialog.Builder(this.context)
                        ad.setIcon(R.drawable.ic_launcher_foreground)
                        ad.setTitle("삭제하시겠습니까?")

                        ad.setNegativeButton("취소") { dialog, _ ->
                            dialog.dismiss()
                        }
                        ad.setPositiveButton("확인") { dialog, _ ->
                            dialog.dismiss()
                            viewModel.deleteSchedule(scheduleArgs.scheduleId)
                            val action = ScheduleDetailFragmentDirections.actionScheduleDetailFragmentToFragmentSchedule()
                            findNavController().navigate(action)
                        }
                        ad.show()
                    }
                }

                true
            }

        }
    }

    private fun initScheduleDetail(){
        viewModel.findSchedule(scheduleArgs.scheduleId)
        viewModel.getSearchResult().observe(viewLifecycleOwner) { schedule ->
            with(binding) {
                tvScheduleName.text = schedule.scheduleName
                tvScheduleAddress.text = schedule.scheduleAddress
                tvScheduleDateStart.text = schedule.scheduleDateBefore
                tvScheduleDateEnd.text = schedule.scheduleDateAfter
                tvScheduleContent.text = schedule.scheduleContent
                //TODO ARTIST
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }


}