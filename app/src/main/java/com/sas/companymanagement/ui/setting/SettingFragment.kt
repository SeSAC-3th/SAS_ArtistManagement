package com.sas.companymanagement.ui.setting

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentSettingBinding
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

/**
 * 설정 화면 프래그먼트
 *
 * @fileName             :SettingFragment.kt
 * @auther               :윤성욱
 * @since                :2023-10-27
 **/
class SettingFragment :
    ViewBindingBaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

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
                    //자동로그인 해제 후 로그인 화면으로 이동
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                    sharedPref?.edit {
                        putBoolean(resources.getString(R.string.auto_login), false)
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
                    toastMessage(resources.getString(R.string.app_name), activity as Activity)
                }
                .launchIn(CoroutineScope(Dispatchers.Main))

            btnSettingVersionInformation
                .flowClicks()
                .throttleFirst(500)
                .onEach {
                    toastMessage(resources.getString(R.string.version_info), activity as Activity)
                }
                .launchIn(CoroutineScope(Dispatchers.Main))
        }
    }

    /**
     * Flow clicks 이벤트
     *
     * @return flow의 값을 리턴
     */
    private fun View.flowClicks(): Flow<Unit> = callbackFlow {
        setOnClickListener {
            trySend(Unit).isSuccess
        }
        awaitClose { setOnClickListener(null) }
    }.buffer(0)

    /**
     * 중복 클릭 방지
     *
     * @param T 타입
     * @param intervalTime 중복 클릭 방지 시간
     * @return flow의 값을 리턴
     */
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