package com.sas.companymanagement.ui.artist.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistRepository

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArtistRepository = ArtistRepository(application)
    private val searchResults: MutableLiveData<Artist> = repository.searchResults
    fun findArtist(id: Long) {
        repository.findArtist(id)
    }
    fun getSearchResults(): MutableLiveData<Artist> {
        return searchResults
    }

    fun deleteArtist(id: Long) {
        repository.deleteArtist(id)
    }
}