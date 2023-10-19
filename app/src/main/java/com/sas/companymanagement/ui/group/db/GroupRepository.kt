package com.sas.companymanagement.ui.group.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.group.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GroupRepository(application: Application) {
    var searchResults = MutableLiveData<List<Group>>()
    var categoryResults = MutableLiveData<List<Group>>()
    private var groupDao: GroupDao
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val allGroups: LiveData<List<Group>>?

    init {
        val db: GroupRoomDatabase = GroupRoomDatabase.getDatabase(application)
        groupDao = db.groupDao()
        allGroups = groupDao.getAllGroup()
    }

    fun insertGroup(newGroup: Group) {
        coroutineScope.launch(Dispatchers.IO) {
            groupDao.insertGroup(newGroup)
        }
    }

    fun deleteGroup(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            groupDao.deleteGroup(id)
        }
    }

    fun findGroup(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async groupDao.findGroup(id)
            }.await()
        }
    }


}