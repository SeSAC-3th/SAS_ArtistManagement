package com.sas.companymanagement.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistBinding
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment

/**
 * Artist Tab
 *
 * @fileName             : ArtistFragment
 * @auther               : 이기영, 윤성욱
 * @since                : 2023-10-17
 **/
class ArtistFragment :
    ViewBindingBaseFragment<FragmentArtistBinding>(FragmentArtistBinding::inflate) {

    private var artistRecyclerView: RecyclerView? = null
    private var artistGridLayoutManager: GridLayoutManager? = null
    private var artistList: ArrayList<Artist>? = null
    private var artistAdapter = ArtistAdapter(mutableListOf(), this)
    private val viewModel: ArtistViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            settingArtistRecyclerView()
            setTabItemMargin(tlArtistCategory, 30)
            tbArtist.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.btnAdd) {
                    val action =
                        ArtistFragmentDirections.actionFragmentArtistToArtistUpdateFragment(-1)
                    findNavController().navigate(action)
                }
                true
            }
        }


        /**
         * 카테고리 항목 별 artist view set up
         *
         * @author 이기영
         */
        binding.tlArtistCategory.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        viewModel.getAllArtist()
                        viewModel.allArtists?.observe(viewLifecycleOwner) { artists ->
                            artistAdapter.setArtistList(artists)
                        }
                    }
                    1 -> {viewModel.findArtistByCategory(ArtistCategory.SINGER.job)}
                    2 -> {viewModel.findArtistByCategory(ArtistCategory.ACTOR.job)}
                    3 -> {viewModel.findArtistByCategory(ArtistCategory.TALENT.job)}
                    else -> throw IllegalStateException()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        viewModel.allArtists?.observe(viewLifecycleOwner) { artists ->
            artistAdapter.setArtistList(artists)
        }
        viewModel.categoryResults.observe(viewLifecycleOwner) { artists ->
            artistAdapter.setArtistList(artists)
        }
    }

    /**
     * Artist RecyclerView 세팅
     * @author 윤성욱
     */
    private fun settingArtistRecyclerView() {
        with(binding) {
            artistRecyclerView = rvArtist
            artistGridLayoutManager =
                GridLayoutManager(
                    context,
                    2,
                    androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                    false
                )
            artistRecyclerView?.layoutManager = artistGridLayoutManager
            artistRecyclerView?.setHasFixedSize(true)
            artistList = ArrayList()
            artistAdapter = ArtistAdapter(artistList!!, requireParentFragment())
            artistRecyclerView?.adapter = artistAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * 탭 레이아웃의 탭 마진 설정
     *
     * @param tabLayout 탭 레이아웃
     * @param marginEnd 마진 값
     * @author 윤성욱
     */
    private fun setTabItemMargin(tabLayout: TabLayout, marginEnd: Int) {
        val tabs = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val lp = tab.layoutParams as LinearLayout.LayoutParams
            lp.marginEnd = marginEnd
            tab.layoutParams = lp
            tabLayout.requestLayout()
        }
    }
}