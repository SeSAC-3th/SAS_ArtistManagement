package com.sas.companymanagement.ui.group

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group_tbl")
data class Group(
    @ColumnInfo(name = "groupName")
    var groupName: String = "",

    @ColumnInfo(name = "groupImage")
    var groupImage: String = "",

    @ColumnInfo(name = "artistId")
    var artistId: String = "",

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
)