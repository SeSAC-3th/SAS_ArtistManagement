package com.sas.companymanagement.ui.group

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sas.companymanagement.ui.artist.Artist

@Entity(tableName = "group_tbl")
data class Group(
    @ColumnInfo(name = "groupName")
    var groupName: String = "",

    @ColumnInfo(name = "groupImage")
    var groupImage: String = "",

    @ColumnInfo(name = "artist")
    var artistId: Int = 0,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "groupId")
    var id: Int = 0,
)