package com.sas.companymanagement.ui.group.update

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sas.companymanagement.ui.group.Group
import com.sas.companymanagement.ui.group.db.GroupRepository

class GroupUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GroupRepository = GroupRepository(application)
    private val allGroups: LiveData<List<Group>>? = repository.allGroups
    private val searchResults: MutableLiveData<List<Group>> = repository.searchResults
    fun insertGroup(group: Group) {
        repository.insertGroup(group)
    }
    fun findGroup(id: Int) {
        repository.findGroup(id)
    }
    fun deleteGroup(id: Int) {
        repository.deleteGroup(id)
    }
    fun getSearchResults(): MutableLiveData<List<Group>> {
        return searchResults
    }

    fun getAllGroups(): LiveData<List<Group>>? {
        return allGroups
    }
}