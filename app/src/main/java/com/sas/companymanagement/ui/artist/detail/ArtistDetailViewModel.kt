package com.sas.companymanagement.ui.artist.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistRepository

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArtistRepository = ArtistRepository(application)
    private val searchResults: MutableLiveData<List<Artist>> = repository.searchResults
    fun findArtist(id: Int) {
        repository.findArtist(id)
    }
    fun getSearchResults(): MutableLiveData<List<Artist>> {
        return searchResults
    }
}