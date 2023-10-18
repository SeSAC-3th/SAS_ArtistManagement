package com.sas.companymanagement.ui.artist.update

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistRepository

class ArtistUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArtistRepository = ArtistRepository(application)
    private val allArtists: LiveData<List<Artist>>? = repository.allArtists
    private val searchResults: MutableLiveData<List<Artist>> = repository.searchResults
    fun insertArtist(artist: Artist) {
        repository.insertArtist(artist)
    }
    fun findArtist(id: Int) {
        repository.findArtist(id)
    }
    fun deleteArtist(id: Int) {
        repository.deleteArtist(id)
    }
    fun getSearchResults(): MutableLiveData<List<Artist>> {
        return searchResults
    }

    fun findArtistByCategory(category: String) {
        repository.findArtistByCategory(category)
    }
    fun getAllArtists(): LiveData<List<Artist>>? {
        return allArtists
    }
}