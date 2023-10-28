package com.sas.companymanagement.ui.group

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
* Group
*
* @fileName         : Group
* @author           : 이기영, 이원형
* @Since            : 2023-10-19
*/

@Entity(tableName = "group_tbl")
data class Group(
    @ColumnInfo(name = "groupName")
    var groupName: String = "",

    @ColumnInfo(name = "groupImage")
    var groupImage: String = "",

    @ColumnInfo(name = "artistId")
    var artistId: String = "",

    @ColumnInfo(name = "groupEval")
    var groupEval: String = "",

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,
)