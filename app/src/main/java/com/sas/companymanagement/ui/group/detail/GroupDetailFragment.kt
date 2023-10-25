package com.sas.companymanagement.ui.group.detail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sas.companymanagement.R
import com.sas.companymanagement.R.menu.menu_detail
import com.sas.companymanagement.databinding.FragmentGroupDetailBinding
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.ArtistAdapter
import com.sas.companymanagement.ui.artist.ArtistViewModel
import com.sas.companymanagement.ui.artist.update.ArtistUpdateViewModel
import com.sas.companymanagement.ui.common.ViewBindingBaseFragment
import com.sas.companymanagement.ui.common.dateToString
import com.sas.companymanagement.ui.group.Group
import com.sas.companymanagement.ui.group.GroupAdapter
import com.sas.companymanagement.ui.schedule.Schedule


class GroupDetailFragment :
    ViewBindingBaseFragment<FragmentGroupDetailBinding>(FragmentGroupDetailBinding::inflate) {


    companion object {
        fun newInstance() = GroupDetailFragment()
    }

    private var artistRecyclerView: RecyclerView? = null
    private val viewModel: GroupDetailViewModel by viewModels()
    private var artistAdapter = ArtistAdapter(mutableListOf(), this)
    private val artistViewModel: ArtistUpdateViewModel by viewModels()
    private val groupArgs: GroupDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerSetup()
        settingArtistRecyclerView()
        fieldSetUp()
    }

    private fun settingArtistRecyclerView() {
        with(binding) {
            artistRecyclerView = rvArtist
            artistAdapter = ArtistAdapter(ArrayList(), requireParentFragment())
            artistRecyclerView?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            artistRecyclerView?.setHasFixedSize(true)
            artistRecyclerView?.adapter = artistAdapter
            /*viewModel.allArtist?.observe(viewLifecycleOwner) { artists ->
                artistAdapter.setArtistList(artists)
            }*/
        }
    }


    private fun listenerSetup() {

        with(binding.rvArtist) {
            layoutManager = GridLayoutManager(this.context, 2).apply {
                canScrollVertically()
            }
//            addItemDecoration(ScheduleItem(context, 10f,35f, Color.CYAN,20f))
            adapter = ArtistAdapter(artistData(), requireParentFragment())
        }

        binding.tbGroup.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.update -> {
                    findNavController().navigate(
                        GroupDetailFragmentDirections.actionGroupDetailFragmentToGroupUpdateFragment(
                            groupArgs.groupId
                        )
                    )
                }

                R.id.delete -> {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("삭제하시겠습니까?")
                        .setPositiveButton("Yes") { dialog, id ->
                            viewModel.deleteGroup(groupArgs.groupId)
                            findNavController().popBackStack()
                        }
                        .setNegativeButton("No") { dialog, id ->

                        }

                    val alertDialog = builder.create()
                    alertDialog.show()
                }

                R.id.menu_update -> {
                    val tb = binding.tbGroup
                    tb.menu.clear()
                    tb.inflateMenu(menu_detail)
                }

            }

            true

        }
    }

    private fun scheduleData() = mutableListOf<Schedule>().apply {
        add(Schedule("2023-10-15", "테스트1"))
        add(Schedule("2023-10-16", "테스트2"))
        add(Schedule("2023-10-17", "테스트3"))
    }

    private fun fieldSetUp() {
        val id = groupArgs.groupId

        val ids = mutableListOf<Artist>()
        viewModel.findGroup(id)
        viewModel.getSearchResults().observe(viewLifecycleOwner) { result ->
            if (result.isNotEmpty()) {
                val group = result[0]
                with(binding) {
                    tbGroup.title = group.groupName
                    iv.setImageURI(group.groupImage.toUri())

                }
                val artistId = group.artistId.split(", ")
                Log.e("info", "$artistId")

                val newArtist = mutableListOf<Artist>()
                artistId.forEach {
                    artistViewModel.findArtistById(it.toLong())
                        .observe(viewLifecycleOwner) { artist ->
                            Log.e("info", "$artist")
                            newArtist.add(artist)
                        }
                    Log.e("info", "${newArtist.size}")

                }

                Log.e("info", "${newArtist.size}")

                artistAdapter.setArtistList(newArtist)
            }
        }
    }

    /* private fun getArtistData() {
         val newArtist = mutableListOf<Artist>()
         artistViewModel.findArtistById()?.observe(viewLifecycleOwner) { artists ->
             artists.forEach { artist ->

                 if ()
                     newArtist.add(artist)

             }

         }
     }*/


    private fun artistData() = mutableListOf<Artist>().apply {
//        add(Artist(R.drawable.dummy_image, "테스트1"))
//        add(Artist(R.drawable.dummy_image, "테스트2"))
//        add(Artist(R.drawable.dummy_image, "테스트3"))
//        add(Artist(R.drawable.dummy_image, "테스트4"))
    }

    /*    override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            viewModel = ViewModelProvider(this).get(GroupDetailViewModel::class.java)
            // TODO: Use the ViewModel
        }*/

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}