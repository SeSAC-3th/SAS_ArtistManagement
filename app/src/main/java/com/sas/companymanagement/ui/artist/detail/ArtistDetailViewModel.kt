package com.sas.companymanagement.ui.artist.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistRepository
import com.sas.companymanagement.ui.group.Group
import com.sas.companymanagement.ui.group.db.GroupRepository
import com.sas.companymanagement.ui.schedule.Schedule
import com.sas.companymanagement.ui.schedule.db.ScheduleRepository

/**
 * Please explain the class!!
 *
 * @fileName             : ArtistDetailViewModel
 * @auther               : 이기영
 * @since                : 2023-10-17
 **/
class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArtistRepository = ArtistRepository(application)
    private val groupRepository: GroupRepository = GroupRepository(application)

    private val searchResults: MutableLiveData<Artist> = repository.searchResults
    val allGroup: LiveData<List<Group>>? = groupRepository.allGroups


    fun findArtist(id: Long) {
        repository.findArtist(id)
    }
    fun getSearchResults(): MutableLiveData<Artist> {
        return searchResults
    }

    fun deleteArtist(id: Long) {
        repository.deleteArtist(id)
    }

    fun updateGroup(group: Group) {
        groupRepository.updateGroupChange(group)
    }



}