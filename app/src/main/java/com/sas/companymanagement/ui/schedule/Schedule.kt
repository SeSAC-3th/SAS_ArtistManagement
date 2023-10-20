package com.sas.companymanagement.ui.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_tbl")
data class Schedule(
    @ColumnInfo(name = "scheduleName")
    var scheduleName: String = "",

    @ColumnInfo(name = "scheduleDateBefore")
    var scheduleDateBefore: String = "",

    @ColumnInfo(name = "scheduleDateAfter")
    var scheduleDateAfter: String = "",

    @ColumnInfo(name = "scheduleAddress")
    var scheduleAddress: String = "",

    @ColumnInfo(name = "scheduleContent")
    var scheduleContent: String = "",


    @ColumnInfo(name = "artistId")
    var artistId: Int? = 0,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "scheduleId")
    var id: Int = 0,
)
