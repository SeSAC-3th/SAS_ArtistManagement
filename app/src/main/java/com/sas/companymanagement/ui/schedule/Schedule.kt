package com.sas.companymanagement.ui.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_tbl")
data class Schedule(
    @ColumnInfo(name = "scheduleName")
    var scheduleName: String = "",

    @ColumnInfo(name = "scheduleBefore")
    var scheduleDateBefore: String = "",

    @ColumnInfo(name = "scheduleAfter")
    var scheduleDateAfter: String = "",

    @ColumnInfo(name = "scheduleAddress")
    var scheduleAddress: String = "",

    @ColumnInfo(name = "scheduleContent")
    var scheduleContent: String = "",

    @ColumnInfo(name = "artistId")
    var artistId: String = "",

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,
)