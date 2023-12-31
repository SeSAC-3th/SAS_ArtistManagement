package com.sas.companymanagement.ui.artist.update

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistRepository

class ArtistUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArtistRepository = ArtistRepository(application)
    private val searchResults: MutableLiveData<Artist> = repository.searchResults

    fun insertArtist(artist: Artist) {
        repository.insertArtist(artist)
    }
    fun findArtist(id: Long) {
        repository.findArtist(id)
    }

    fun getSearchResults(): MutableLiveData<Artist> {
        return searchResults
    }

    fun updateArtist(artist: Artist) {
        repository.updateUser(artist)
    }
    fun findArtistById(id: Long): LiveData<Artist> {
        return repository.findArtistById(id)
    }
}