package com.sas.companymanagement.ui.artist.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistSelectBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.artist.ArtistCategory
import com.sas.companymanagement.ui.artist.ArtistViewModel
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment

/**
* Please explain the class!!
*
* @fileName             :ArtistSelectFragment.kt
* @auther               :윤성욱
* @since                :2023-10-27
**/
class ArtistSelectFragment :
    ViewBindingBaseFragment<FragmentArtistSelectBinding>(FragmentArtistSelectBinding::inflate) {

    private var artistSelectRecyclerView: RecyclerView? = null
    private var artistSelectGridLayoutManager: GridLayoutManager? = null
    private var artistSelectList: ArrayList<Artist>? = null
    private var artistSelectAdapter = ArtistAdapter(mutableListOf(), this)
    private val viewModel: ArtistViewModel by viewModels()
    private val artistSelectedArgs: ArtistSelectFragmentArgs by navArgs()
    private lateinit var selectedArtistIdList: MutableSet<Long>

    companion object {
        fun newInstance() = ArtistSelectFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            settingArtistRecyclerView()
            setTabItemMargin(tlArtistSelectCategory, 30)
        }

        binding.tbArtistSelect.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menu_update) {
                selectedArtistIdList = artistSelectAdapter.getSelectedId()
                if (artistSelectedArgs.from == resources.getString(R.string.from_group)){
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("selectedArtistId", selectedArtistIdList)
                   findNavController().popBackStack()
                }
                if (artistSelectedArgs.from == resources.getString(R.string.from_schedule)){
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("selectedArtistId", selectedArtistIdList)
                    findNavController().popBackStack()
                }

            }
            true
        }

        binding.tlArtistSelectCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> { /*ALL*/
                        viewModel.getAllArtist()
                        // allArtist 는 데이터 변화가 없기 때문에 다시 view에 보여주기 위하여 해당 코드 작성
                        viewModel.allArtists?.observe(viewLifecycleOwner) { artists ->
                            artistSelectAdapter.setArtistList(artists)
                        }
                    }
                    1 -> {viewModel.findArtistByCategory(ArtistCategory.SINGER.job) }
                    2 -> {viewModel.findArtistByCategory(ArtistCategory.ACTOR.job) }
                    3 -> {viewModel.findArtistByCategory(ArtistCategory.TALENT.job) }
                    else -> throw IllegalStateException()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        viewModel.allArtists?.observe(viewLifecycleOwner) { artists ->
            artistSelectAdapter.setArtistList(artists)
        }
        viewModel.categoryResults.observe(viewLifecycleOwner) { artists ->
            artistSelectAdapter.setArtistList(artists)
        }
    }

    /**
     * Artist RecyclerView 세팅
     * @author 윤성욱
     */
    private fun settingArtistRecyclerView() {
        with(binding) {
            artistSelectRecyclerView = rvArtistSelect
            artistSelectGridLayoutManager =
                GridLayoutManager(
                    context,
                    2,
                    androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                    false
                )
            artistSelectRecyclerView?.layoutManager = artistSelectGridLayoutManager
            artistSelectRecyclerView?.setHasFixedSize(true)

            artistSelectList = ArrayList()
            artistSelectAdapter = ArtistAdapter(artistSelectList!!, requireParentFragment())
            artistSelectRecyclerView?.adapter = artistSelectAdapter
        }
    }

    /**
     * 탭의 마진을 변경
     * @param tabLayout 적용할 탭 레이아웃
     * @param marginEnd 마진 길이
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