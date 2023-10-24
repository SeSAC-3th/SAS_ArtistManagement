package com.sas.companymanagement.ui.setting

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentGroupBinding
import com.sas.companymanagement.databinding.FragmentSettingBinding
import com.sas.companymanagement.ui.common.toastMessage
import com.sas.companymanagement.ui.login.LoginFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnSettingLogout
                .flowClicks()
                .throttleFirst(500)
                .onEach {
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                    sharedPref?.edit {
                        putBoolean("autoLogin", false)
                        apply()
                    }
                    val action = SettingFragmentDirections.actionFragmentSettingToLoginFragment()
                    findNavController().navigate(action)
                }
                .launchIn(CoroutineScope(Dispatchers.Main))

            btnSettingHelp
                .flowClicks()
                .throttleFirst(500)
                .onEach {
                    toastMessage("CompanyManagement", activity as Activity)
                }
                .launchIn(CoroutineScope(Dispatchers.Main))

            btnSettingVersionInformation
                .flowClicks()
                .throttleFirst(500)
                .onEach {
                    toastMessage("0.1 version", activity as Activity)
                }
                .launchIn(CoroutineScope(Dispatchers.Main))

        }
    }

    private fun View.flowClicks(): Flow<Unit> = callbackFlow {
        setOnClickListener {
            trySend(Unit).isSuccess
        }
        awaitClose { setOnClickListener(null) }
    }.buffer(0)

    private fun <T> Flow<T>.throttleFirst(intervalTime: Long): Flow<T> = flow {
        var throttleTime = 0L
        collect { upStream ->
            val currentTime = System.currentTimeMillis()
            if ((currentTime - throttleTime) > intervalTime) {
                throttleTime = currentTime
                emit(upStream)
            }
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}