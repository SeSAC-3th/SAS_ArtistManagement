package com.sas.companymanagement.ui.artist.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sas.companymanagement.R

class ArtistUpdateFragment : Fragment() {

    companion object {
        fun newInstance() = ArtistUpdateFragment()
    }

    private lateinit var viewModel: ArtistUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artist_update, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtistUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}