package com.sas.companymanagement.ui.group.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistRepository
import com.sas.companymanagement.ui.group.Group
import com.sas.companymanagement.ui.group.db.GroupRepository


class GroupDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GroupRepository = GroupRepository(application)
    private val searchResults: MutableLiveData<Group> = repository.searchResults

    private val artistRepository: ArtistRepository = ArtistRepository(application)

    fun findGroup(id: Long) {
        repository.findGroup(id)
    }

    fun getSearchResults(): MutableLiveData<Group> {
        return searchResults
    }

    fun deleteGroup(id: Long) {
        repository.deleteGroup(id)
    }

    fun getAllArtist(): LiveData<List<Artist>>? {
        return artistRepository.getAllArtist()
    }
}