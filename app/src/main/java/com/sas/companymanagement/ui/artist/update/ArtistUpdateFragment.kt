package com.sas.companymanagement.ui.artist.update


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistUpdateBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.artist.ArtistFragmentDirections
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class ArtistUpdateFragment :
    ViewBindingBaseFragment<FragmentArtistUpdateBinding>(FragmentArtistUpdateBinding::inflate) {

    companion object {
        fun newInstance() = ArtistUpdateFragment()
    }

    private val viewModel: ArtistUpdateViewModel by viewModels()
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
        listenerSetup()
        observerSetup()
//        uiSetup()
    }

    private fun clearFields() {
        with(binding) {
            teArtistName.setText("")
            teArtistNickname.setText("")

        }
    }

    @SuppressLint("SetTextI18n")
    private fun listenerSetup() {
        binding.tbArtistUpdate.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menu_add) {
                val name = binding.teArtistName.text.toString()
                val nickname = binding.teArtistNickname.text.toString()
                if (name.isNotEmpty()) {
//                    viewModel.getAllArtists2()
                    viewModel.insertArtist(Artist(name, nickname))
                    clearFields()
                }
            }
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observerSetup() {
        viewModel.getAllArtists()?.observe(viewLifecycleOwner) { Artists ->
            Log.e("Insert",Artists.get(0).artistName.toString())
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