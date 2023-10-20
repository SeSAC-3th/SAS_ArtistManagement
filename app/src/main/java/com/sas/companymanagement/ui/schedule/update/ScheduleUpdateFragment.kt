package com.sas.companymanagement.ui.schedule.update

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentScheduleUpdateBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistFragmentDirections
import com.sas.companymanagement.ui.artist.detail.ArtistDetailViewModel
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.schedule.Schedule
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges as flowTextWatcher


class ScheduleUpdateFragment :
    ViewBindingBaseFragment<FragmentScheduleUpdateBinding>(FragmentScheduleUpdateBinding::inflate) {

    companion object {
        fun newInstance() = ScheduleUpdateFragment()
    }

    private lateinit var viewModel: ScheduleUpdateViewModel

    private val compositeDisposable = CompositeDisposable()
    private val defaultScope = CoroutineScope(Dispatchers.Default)


    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleUpdateViewModel::class.java)

        with(binding.tbScheduleUpdate) {
            title = "스케쥴 추가"
            menu.findItem(R.id.menu_add).setIcon(R.drawable.ic_check_24)
        }

        with(compositeDisposable) {
            with(binding) {
                calendarButton
                    .clicks()
                    .observeOn(Schedulers.io())
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        showDateTimePicker(
                            scheduleDatePicker,
                            scheduleTimePicker,
                            scheduleTimeFormatTv
                        )
                    }, {
                        Log.e("RX_ERROR", compositeDisposable.toString())
                    })

                calendarAfterButton
                    .clicks()
                    .observeOn(Schedulers.io())
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        showDateTimePicker(
                            scheduleAfterDatePicker,
                            scheduleAfterTimePicker,
                            scheduleAfterTimeFormat
                        )
                    }, {
                        Log.e("RX_ERROR", compositeDisposable.toString())
                    })

                addChip
                    .clicks()
                    .observeOn(Schedulers.io())
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        addArtistChip()

                    }, {
                        Log.e("RX_ERROR", compositeDisposable.toString())
                    })



            }
        }
        listenerSetup()
        observerSetup()


        /*        with(binding){
                    edScheduleName.flowTextWatcher()
                        .debounce(300)
                        .onEach {

                        }
                        .launchIn(defaultScope)

                }*/
    }
    private fun clearFields() {
        with(binding){
            edScheduleName.setText("")
            edSchedulePlaceName.setText("")
            edSchedulePlaceName.setText("초기")
            scheduleTimeFormatTv.text = "초기"
            scheduleTimePicker.text = "초기"
        }
    }
    private fun listenerSetup() {
        binding.tbScheduleUpdate.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menu_add) {
                val name = binding.edScheduleName.text.toString()
                val place = binding.edSchedulePlaceName.text.toString()
                val date =
                    binding.scheduleDatePicker.text.toString() + binding.scheduleTimeFormatTv.text.toString() + binding.scheduleTimePicker.text.toString()
                if (name.isNotEmpty()) {
                    viewModel.insertSchedule(Schedule(name, place, date))
                }
            }
            true
        }
    }


    private fun observerSetup() {
        viewModel.getAllSchedules()?.observe(viewLifecycleOwner) { Schedules ->
            for (item in Schedules.indices) {
                Log.e("Insert",Schedules.get(item).scheduleName.toString())
            }
        }
    }

    private fun showDateTimePicker(
        dateTextView: TextView,
        timeTextView: TextView,
        amPmTextView: TextView
    ) {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener { selectedDateValue ->
            val selectDate = Calendar.getInstance()
            selectDate.timeInMillis = selectedDateValue

            val timePicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .build()

            timePicker.addOnPositiveButtonClickListener {
                val hour = timePicker.hour
                val minute = timePicker.minute

                selectDate.set(Calendar.HOUR_OF_DAY, hour)
                selectDate.set(Calendar.MINUTE, minute)

                dateTextView.text =
                    SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(selectDate.time)
                timeTextView.text =
                    String.format(Locale.getDefault(), "%02d:%02d", hour, minute)

                amPmTextView.text = if (hour < 12) "오전" else "오후"
            }
            timePicker.show(childFragmentManager, "time_picker_tag")
        }
        datePicker.show(childFragmentManager, "time_picker_tag")


    }

    private fun addArtistChip() {
        var chipName = "이종윤"

        /*        binding.chipGroup.removeView(binding.addChip)

                binding.chipGroup.addView(Chip(context).apply {
                    chipIcon = ContextCompat.getDrawable(context, R.drawable.ic_android_24)
                    text = chipName
                    isCloseIconVisible = true
                    setOnCloseIconClickListener{binding.chipGroup.removeView(this)}
                })

                binding.chipGroup.addView(binding.addChip)*/

        val action = ScheduleUpdateFragmentDirections.actionScheduleUpdateFragmentToFragmentArtist()
        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}



