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
    private val allSchedule : LiveData<List<Schedule>>? = repository.allSchedules
    private val searchResults : MutableLiveData<List<Schedule>> = repository.searchResults

    var scheduleDate: String = "00년 00월 00일"
    var scheduleTime: String = "00:00"
    var scheduleAmPm: String = "오전"

    var scheduleAfterDate: String = "00년 00월 00일"
    var scheduleAfterTime: String = "00:00"
    var scheduleAfterAmPm: String = "오전"

    fun insertSchedule(schedule: Schedule){
        repository.insertSchedule(schedule)
    }

    fun findSchedule(id : Int){
        repository.findSchedule(id)
    }

    fun deleteSchedule(id : Int){
        repository.deleteSchedule(id)
    }

    fun getSearchSchedules() : MutableLiveData<List<Schedule>>{
        return searchResults
    }

    fun getAllSchedules() : LiveData<List<Schedule>>?{
        return allSchedule
    }

    fun updateSchedule(newSchedule: Schedule) {
        repository.updateSchedule(newSchedule)
    }

    fun findScheduleById(id: Int): LiveData<Schedule> {
        return repository.findScheduleById(id)
    }


}