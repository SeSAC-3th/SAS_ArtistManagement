package com.sas.companymanagement.ui.group

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sas.companymanagement.ui.group.db.GroupRepository

/**
* GroupViewModel
*
* @fileName         : GroupViewModel.kt
* @author           : 이원형
* @Since            : 2023-10-22
*/
class GroupViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GroupRepository = GroupRepository(application)
    val allGroup : LiveData<List<Group>>? = repository.allGroups
}