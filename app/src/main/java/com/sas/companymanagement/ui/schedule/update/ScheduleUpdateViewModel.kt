package com.sas.companymanagement.ui.schedule.update

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sas.companymanagement.ui.schedule.Schedule
import com.sas.companymanagement.ui.schedule.db.ScheduleRepository

class ScheduleUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : ScheduleRepository = ScheduleRepository(application)

    fun insertSchedule(schedule: Schedule){
        repository.insertSchedule(schedule)
    }


    fun updateSchedule(newSchedule: Schedule) {
        repository.updateSchedule(newSchedule)
    }

    fun findScheduleById(id: Int): LiveData<Schedule> {
        return repository.findScheduleById(id)
    }


}