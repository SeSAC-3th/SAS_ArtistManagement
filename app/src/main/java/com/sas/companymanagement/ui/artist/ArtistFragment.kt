package com.sas.companymanagement.ui.artist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.sas.companymanagement.R
import com.sas.companymanagement.databinding.FragmentArtistBinding

class ArtistFragment : Fragment() {
    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!

    private var artistRecyclerView: RecyclerView? = null
    private var artistGridLayoutManager: GridLayoutManager? = null
    private var artistList: ArrayList<Artist>? = null
    private var artistAdapter: ArtistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            artistRecyclerView = rvArtist
            artistGridLayoutManager =
                GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            artistRecyclerView?.layoutManager = artistGridLayoutManager
            artistRecyclerView?.setHasFixedSize(true)
            artistList = ArrayList()
            artistList = setDatainList()
            artistAdapter = ArtistAdapter(requireContext(), artistList!!)
            artistRecyclerView?.adapter = artistAdapter
            setTabItemMargin(category, 30)
        }

        val tabLayout = binding.category
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val param = when (tab.position) {
                    0 -> { /*ALL*/
                    }

                    1 -> {/*가수*/
                    }

                    2 -> {/*배우*/
                    }

                    3 -> {/*탤런트*/
                    }

                    else -> throw IllegalStateException()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }

    private fun setDatainList(): ArrayList<Artist> {

        var items: ArrayList<Artist> = ArrayList()

        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))
        items.add(Artist(R.drawable.ic_add_circle_24, "김채원"))

        return items
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

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