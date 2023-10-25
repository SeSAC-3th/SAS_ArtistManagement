package com.sas.companymanagement.ui.group.update

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.group.Group
import com.sas.companymanagement.ui.group.db.GroupRepository

class GroupUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GroupRepository = GroupRepository(application)
    private val searchResults: MutableLiveData<Group> = repository.searchResults
    fun insertGroup(group: Group) {
        repository.insertGroup(group)
    }

    fun findGroup(id: Long) {
        repository.findGroup(id)
    }


    fun getSearchResults(): MutableLiveData<Group> {
        return searchResults
    }


    fun updateGroup(group: Group) {
        repository.updateGroupChange(group)
    }
}