package com.sas.companymanagement.ui.group

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "group_tbl")
class Group {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "groupId")
    var id: Int = 0

    @ColumnInfo(name = "groupName")
    var groupName: String? = null


    @ColumnInfo(name = "groupImage")
    var groupImage: String? = null
    constructor() {}
    @Ignore
    constructor(id: Int, groupName: String, groupImage: String) {
        this.id = id
        this.groupName = groupName
        this.groupImage = groupImage
    }
    @Ignore
    constructor(groupName: String, groupImage: String) {
        this.groupName = groupName
        this.groupImage = groupImage
    }
}