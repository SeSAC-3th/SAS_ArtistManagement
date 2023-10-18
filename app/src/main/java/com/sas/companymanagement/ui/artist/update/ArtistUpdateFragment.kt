package com.sas.companymanagement.ui.artist.update


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.material.datepicker.MaterialDatePicker
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.databinding.FragmentArtistUpdateBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class ArtistUpdateFragment :
    ViewBindingBaseFragment<FragmentArtistUpdateBinding>(FragmentArtistUpdateBinding::inflate) {

    companion object {
        fun newInstance() = ArtistUpdateFragment()
    }

    private lateinit var viewModel: ArtistUpdateViewModel
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(compositeDisposable) {
            with(binding) {
                tvArtistBirth
                    .clicks()
                    .observeOn(Schedulers.io())
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        showDateTimePicker(
                            tvArtistBirth
                        )
                    }, {
                        Log.e("RX_ERROR", compositeDisposable.toString())

                    })

            }
        }

    }

    private fun showDateTimePicker(
        dateTextView: TextView
    ) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selectedDateValue ->
            val selectDate = Calendar.getInstance()
            selectDate.timeInMillis = selectedDateValue
            dateTextView.text =
                SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(selectDate.time)
        }
        datePicker.show(childFragmentManager, "time_picker_tag")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

}