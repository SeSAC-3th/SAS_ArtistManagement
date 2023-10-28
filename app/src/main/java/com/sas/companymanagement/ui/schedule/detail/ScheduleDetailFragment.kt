package com.sas.companymanagement.ui.schedule.detail

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentScheduleDetailBinding
import com.sas.companymanagement.ui.artist.update.ArtistUpdateViewModel
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
/**
 * Schedule Tab
 *
 * @fileName             : ScheduleDetailFragment
 * @auther               : 박지혜, 이기영
 * @since                : 2023-10-18
 **/
class ScheduleDetailFragment :
    ViewBindingBaseFragment<FragmentScheduleDetailBinding>(FragmentScheduleDetailBinding::inflate) {

    companion object {
        fun newInstance() = ScheduleDetailFragment()
    }

    private val viewModel: ScheduleDetailViewModel by viewModels()
    private val scheduleArgs: ScheduleDetailFragmentArgs by navArgs()
    private val artistViewModel: ArtistUpdateViewModel by viewModels()

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

    /**
     * 메뉴 선택시 수정페이지로 이동, 삭제 기능
     * @author 박지혜
     */
    private fun initMenu() {
        with(binding.tbScheduleDetail) {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
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
                            val action =
                                ScheduleDetailFragmentDirections.actionScheduleDetailFragmentToFragmentSchedule()
                            findNavController().navigate(action)
                        }
                        ad.show()
                    }
                }

                true
            }

        }
    }
    /**
     * 스케쥴 디테일 초기화
     * @author 박지혜, 이기영
     */
    private fun initScheduleDetail() {
        viewModel.findSchedule(scheduleArgs.scheduleId)
        viewModel.getSearchResult().observe(viewLifecycleOwner) { schedule ->
            with(binding) {
                tvScheduleName.text = schedule.scheduleName
                tvScheduleAddress.text = schedule.scheduleAddress
                tvScheduleDateStart.text = schedule.scheduleDateBefore
                tvScheduleDateEnd.text = schedule.scheduleDateAfter
                tvScheduleContent.text = schedule.scheduleContent

                val artistIds = schedule.artistId.split(", ")
                tvScheduleArtist.text = ""
                artistIds.forEach {
                    if (it.isNotEmpty()) {
                        artistViewModel.findArtistById(it.toLong())
                            .observe(viewLifecycleOwner) { artist ->
                                tvScheduleArtist.append(artist.artistName + " ")
                            }

                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }


}