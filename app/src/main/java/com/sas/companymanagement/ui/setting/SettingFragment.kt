package com.sas.companymanagement.ui.setting

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.sas.companymanagement.databinding.FragmentSettingBinding
import com.sas.companymanagement.ui.common.HELP
import com.sas.companymanagement.ui.common.VERSIONINFO
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.common.toastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingFragment : ViewBindingBaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private val autoLoginKey = "autoLogin"

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
                        putBoolean(autoLoginKey, false)
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
                    toastMessage(HELP, activity as Activity)
                }
                .launchIn(CoroutineScope(Dispatchers.Main))

            btnSettingVersionInformation
                .flowClicks()
                .throttleFirst(500)
                .onEach {
                    toastMessage(VERSIONINFO, activity as Activity)
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