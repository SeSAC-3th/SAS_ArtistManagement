package com.sas.companymanagement.ui.schedule.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.schedule.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ScheduleRepository(application: Application) {
    var searchResults = MutableLiveData<Schedule>()
    private var scheduleDao : ScheduleDao
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val allSchedules : LiveData<List<Schedule>>?

    init {
        val db : ScheduleRoomDatabase = ScheduleRoomDatabase.getDatabase(application)
        scheduleDao = db.scheduleDao()
        allSchedules = scheduleDao.getAllSchedule()
    }

//    fun getAllSchedules() : LiveData<List<Schedule>>? {
//        return allSchedules
//    }

    fun insertSchedule(newSchedule: Schedule){
        coroutineScope.launch {
            scheduleDao.insertSchedule(newSchedule)
        }
    }

    fun deleteSchedule(id:Long){
        coroutineScope.launch {
            scheduleDao.deleteSchedule(id)
        }
    }
    fun findSchedule(id: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            searchResults.value = coroutineScope.async {
                return@async scheduleDao.findSchedule(id)
            }.await()
        }
    }

    fun updateSchedule(newSchedule: Schedule) {
        coroutineScope.launch {
            scheduleDao.updateSchedule(newSchedule)
        }
    }

    fun findScheduleById(id: Long): LiveData<Schedule> {
        return scheduleDao.findScheduleById(id)
    }
}
