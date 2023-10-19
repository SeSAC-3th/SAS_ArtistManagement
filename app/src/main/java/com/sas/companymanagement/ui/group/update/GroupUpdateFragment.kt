package com.sas.companymanagement.ui.group.update

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding4.view.clicks
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupUpdateBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.group.Group
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GroupUpdateFragment :
    ViewBindingBaseFragment<FragmentGroupUpdateBinding>(FragmentGroupUpdateBinding::inflate) {


    companion object {
        fun newInstance() = GroupUpdateFragment()
    }

    private val viewModel: GroupUpdateViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(compositeDisposable) {
            with(binding) {
                cAdd
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
    }

    private fun clearFields() {
        with(binding) {
            teGroupName.setText("")
        }
    }

    private fun addArtistChip() {
        var chipName = "이원형"

        binding.cgArtistUpdate.removeView(binding.cAdd)

        binding.cgArtistUpdate.addView(Chip(context).apply {
            chipIcon = ContextCompat.getDrawable(context, R.drawable.ic_check_24)
            text = chipName
            isCloseIconVisible = true
            setOnCloseIconClickListener { binding.cgArtistUpdate.removeView(this) }
        })
        binding.cgArtistUpdate.addView(binding.cAdd)
    }

    @SuppressLint("SetTextI18n")
    private fun listenerSetup() {
        binding.tbGroupUpdate.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menu_update) {
                val name = binding.teGroupName.text.toString()
                if (name.isNotEmpty()) {
                    viewModel.getAllGroups()
                    viewModel.insertGroup(Group(name, ""))
                    clearFields()
                }
            }
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observerSetup() {
        viewModel.getAllGroups()?.observe(viewLifecycleOwner) { Groups ->
            for (item in Groups.indices) Log.e("Insert", Groups.get(item).groupName.toString())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}