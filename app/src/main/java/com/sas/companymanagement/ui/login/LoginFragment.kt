package com.sas.companymanagement.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.sas.companymanagement.R
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

/**
 * 로그인 화면 프래그먼트
 *
 * @fileName             :LoginFragment.kt
 * @auther               :윤성욱
 * @since                :2023-10-27
 **/
class LoginFragment : ViewBindingBaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val id = resources.getString(R.string.login_id)
    private val password = resources.getString(R.string.login_password)
    private val autoLogin = resources.getString(R.string.auto_login)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnLogin
                .flowClicks()
                .throttleFirst(500)
                .onEach {
                    if ((etLoginId.text.toString() == id) && (etLoginPassword.text.toString() == password)) {
                        checkAutoLogin(binding.checkBoxAutoLogin)
                        val action = LoginFragmentDirections.actionLoginFragmentToFragmentMain()
                        findNavController().navigate(action)
                    } else {
                        toastMessage(
                            resources.getString(R.string.login_wrong_text),
                            activity as Activity
                        )
                    }
                }
                .launchIn(CoroutineScope(Dispatchers.Main))
        }
        onBackPressed()
    }

    /**
     * Check auto login
     * 자동로그인 체크박스 확인
     * @param view 체크박스
     * @author 윤성욱
     */
    private fun checkAutoLogin(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            if (checked) {
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putBoolean(autoLogin, true)
                    apply()
                }
            } else {
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()) {
                    putBoolean(autoLogin, false)
                    apply()
                }
            }
        }
    }

    /**
     * Flow clicks 이벤트
     *
     * @return flow의 값을 리턴
     * @author 윤성욱
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
     * @author 윤성욱
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

    /**
     * 뒤로가기 버튼 눌렀을 때 처리
     * @author 윤성욱
     */
    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}