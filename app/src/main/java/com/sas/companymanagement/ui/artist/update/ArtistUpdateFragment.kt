package com.sas.companymanagement.ui.artist.update


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistUpdateBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistCategory
import com.sas.companymanagement.ui.artist.ArtistGender
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Locale.Category
import java.util.concurrent.TimeUnit

class ArtistUpdateFragment :
    ViewBindingBaseFragment<FragmentArtistUpdateBinding>(FragmentArtistUpdateBinding::inflate) {

    companion object {
        fun newInstance() = ArtistUpdateFragment()
    }

    private val viewModel: ArtistUpdateViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private var name = ""
    private var birth = ""
    private var gender = ""
    private var nickname = ""
    private var category = ""
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
    }

    private fun clearFields() {
        with(binding) {
            teArtistName.setText("")
            teArtistNickname.setText("")
        }
    }

    private fun fieldSetup() {
        name = binding.teArtistName.text.toString()
        nickname = binding.teArtistNickname.text.toString()
        // 갤러리 호출하여 저장
        // val imageSrc = ~~
        birth = binding.tvArtistBirth.text.toString()
    }

    private fun listenerSetup() {
        with(binding) {
            tbArtistUpdate.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            rgArtistGender.setOnCheckedChangeListener { group, checkdId ->
                gender = when (checkdId) {
                    R.id.radioButtonFemale -> ArtistGender.FEMALE.gender
                    else -> ArtistGender.MALE.gender
                }
            }
            spArtistJob.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        category = when (parent!!.getItemAtPosition(position).toString()) {
                            ArtistCategory.ACTOR.job -> ArtistCategory.ACTOR.job
                            ArtistCategory.TALENT.job -> ArtistCategory.TALENT.job
                            else -> ArtistCategory.SINGER.job
                        }
                    }
                }
            tbArtistUpdate.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menu_update) {
                    fieldSetup()
                    if (name.isNotEmpty() && nickname.isNotEmpty() && birth.isNotEmpty()) {
                        viewModel.insertArtist(
                            Artist(
                                artistName = name,
                                artistNickname = nickname,
                                artistImage = "src",
                                artistGender = gender,
                                artistCategory = category,
                                artistBirth = birth
                            )
                        )
                        clearFields()
                        findNavController().popBackStack()
                    } else { //TODO 예외처리
                    }
                }
                true
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun observerSetup() {
        viewModel.getAllArtists()?.observe(viewLifecycleOwner) { Artists ->
            for (item in Artists.indices) {
                Log.e("Insert", Artists.get(item).id.toString())
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