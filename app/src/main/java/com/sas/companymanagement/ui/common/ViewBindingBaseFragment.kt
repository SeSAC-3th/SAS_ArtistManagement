package com.sas.companymanagement.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


/**
 * 모든 Fragment 은 해당 클래스를 상속
 * onCreateView 중복 코드줄이기 위함
 * @author 이종윤
 */
typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class ViewBindingBaseFragment<VB : ViewBinding>(
    private val inflate: FragmentInflate<VB>
) : Fragment() {

    var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // null처리 하지 않으면 viewBinding 객체가 계속 살아있게 됨
    }
}

