package com.sas.companymanagement.ui.schedule.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sas.companymanagement.ui.schedule.Schedule

@Dao
interface ScheduleDao {
    @Insert
    fun insertSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule_tbl WHERE scheduleId = :id")
    fun findSchedule(id: Int): List<Schedule>

    @Update
    fun updateSchedule(vararg schedules: Schedule)

    @Query(value = "DELETE FROM schedule_tbl WHERE scheduleId = :id")
    fun deleteSchedule(id: Int)

    @Query("SELECT * FROM schedule_tbl")
    fun getAllSchedule(): LiveData<List<Schedule>>
}