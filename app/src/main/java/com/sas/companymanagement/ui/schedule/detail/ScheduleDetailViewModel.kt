package com.sas.companymanagement.ui.schedule.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.schedule.Schedule
import com.sas.companymanagement.ui.schedule.db.ScheduleRepository

class ScheduleDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ScheduleRepository = ScheduleRepository(application)
    private val searchResult: MutableLiveData<Schedule> = repository.searchResults


    fun findSchedule(id: Long) {
        repository.findSchedule(id)
    }

    fun deleteSchedule(id: Long){
        repository.deleteSchedule(id)
    }

    fun getSearchResult(): MutableLiveData<Schedule> {
        return searchResult
    }
}