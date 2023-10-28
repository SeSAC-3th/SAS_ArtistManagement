package com.sas.companymanagement.ui.group.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sas.companymanagement.ui.group.Group

/**
* groupDao
*
* @fileName         : GroupDao
* @author           : 이기영, 이원형
* @Since            : 2023-10-18
*/

@Dao
interface GroupDao {
    @Insert
    fun insertGroup(group: Group)

    @Query("SELECT * FROM group_tbl WHERE id = :id")
    fun findGroup(id: Long): Group

    @Update
    fun updateGroup(vararg groups: Group)

    @Query(value = "DELETE FROM group_tbl WHERE id = :id")
    fun deleteGroup(id: Long)

    @Query("SELECT * FROM group_tbl")
    fun getAllGroup(): LiveData<List<Group>>

    @Query("SELECT * FROM group_tbl WHERE id = :id")
    fun findByIdGroup(id: Long): LiveData<Group>
}