package com.sas.companymanagement.ui.group.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistRepository
import com.sas.companymanagement.ui.group.Group
import com.sas.companymanagement.ui.group.db.GroupRepository

//class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {

class GroupDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GroupRepository = GroupRepository(application)
    private val searchResults: MutableLiveData<List<Group>> = repository.searchResults

    private val artistRepository: ArtistRepository = ArtistRepository(application)
    val allArtist: LiveData<List<Artist>>? = artistRepository.allArtists

    fun findGroup(id: Int) {
        repository.findGroup(id)
    }

    fun getSearchResults(): MutableLiveData<List<Group>> {
        return searchResults
    }

    fun deleteGroup(id: Int) {
        repository.deleteGroup(id)
    }
    fun findArtist(id: Int) {

    }

}