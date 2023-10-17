package com.sas.companymanagement.ui.schedule

class Schedule {

    var scheduleDate: String? = null
    var scheduleName: String? = null

    constructor(scheduleName: String?, scheduleDate: String?) {
        this.scheduleName = scheduleName
        this.scheduleDate = scheduleDate
    }
}