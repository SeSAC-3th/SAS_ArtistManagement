package com.sas.companymanagement.ui.schedule.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
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

    private lateinit var viewModel: ScheduleDetailViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)

        listenerSetup()
        return binding.root
    }

    private fun listenerSetup() {
        binding.tbScheduleDetail.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.update) {
                val action = ScheduleDetailFragmentDirections.actionScheduleDetailFragmentToScheduleUpdateFragment(0)
                findNavController().navigate(action)
            }
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }


}