package com.sas.companymanagement.ui.schedule

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "schedule_tbl")
class Schedule {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "scheduleId")
    var id: Int = 0

    @ColumnInfo(name = "scheduleName")
    var scheduleName: String? = null

    @ColumnInfo(name = "scheduleDate")
    var scheduleDate: String? = null

    @ColumnInfo(name = "scheduleAddress")
    var scheduleAddress: String? = null

    @ColumnInfo(name = "scheduleContent")
    var scheduleContent: String? = null

    /*@ColumnInfo(name = "scheduleArtists")
    var scheduleArtists:List<String>? = null*/

    constructor() {}
    @Ignore
    constructor(id: Int, scheduleName: String, scheduleAddress: String) {
        this.id = id
        this.scheduleName = scheduleName
        this.scheduleAddress = scheduleAddress
    }

    @Ignore
    constructor(scheduleName: String, scheduleAddress: String) {
        this.scheduleName = scheduleName
        this.scheduleAddress = scheduleAddress

    }
    @Ignore
    constructor(scheduleName: String, scheduleAddress: String,scheduleDates : String) {
        this.scheduleName = scheduleName
        this.scheduleAddress = scheduleAddress
        this.scheduleDate = scheduleDates
    }
}
