package com.sas.companymanagement.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistBinding
import com.sas.companymanagement.databinding.FragmentLoginBinding
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

class LoginFragment : ViewBindingBaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val autoLoginData = sharedPref.getBoolean("autoLogin",false)
        Log.e("return", autoLoginData.toString())

        with(binding) {
                    btnLogin
                        .flowClicks()
                        .throttleFirst(500)
                        .onEach {
                            if ((etLoginId.text.toString() == "admin") && (etLoginPassword.text.toString() == "123456")) {
                                val action = LoginFragmentDirections.actionLoginFragmentToFragmentMain()
                                findNavController().navigate(action)
                            } else {
                                toastMessage("잘못된 입력입니다.", activity as Activity)
                            }
                        }
                        .launchIn(CoroutineScope(Dispatchers.Main))

            onCheckboxClicked(binding.checkBoxAutoLogin)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            if (checked) {
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putBoolean("autoLogin", true)
                    apply()
                }
            } else {
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putBoolean("autoLogin", false)
                    apply()
                }
            }
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

}