package com.sas.companymanagement.ui.group

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sas.companymanagement.ui.group.db.GroupRepository

class GroupViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GroupRepository = GroupRepository(application)
    val allGroup : LiveData<List<Group>>? = repository.allGroups
}