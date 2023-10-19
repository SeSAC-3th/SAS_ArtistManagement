package com.sas.companymanagement.ui.group.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sas.companymanagement.ui.group.Group

@Dao
interface GroupDao {
    @Insert
    fun insertGroup(group: Group)

    @Query("SELECT * FROM group_tbl WHERE groupId = :id")
    fun findGroup(id: Int): List<Group>

    @Update
    fun updateGroup(vararg groups: Group)

    @Query(value = "DELETE FROM group_tbl WHERE groupId = :id")
    fun deleteGroup(id: Int)

    @Query("SELECT * FROM group_tbl")
    fun getAllGroup(): LiveData<List<Group>>
}