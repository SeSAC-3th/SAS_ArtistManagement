package com.sas.companymanagement.ui.artist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sas.companymanagement.ui.artist.db.ArtistRepository

class ArtistViewModel(application: Application) : AndroidViewModel(application){
    private val repository: ArtistRepository = ArtistRepository(application)
    val allArtists: LiveData<List<Artist>>? = repository.getAllArtist()
}