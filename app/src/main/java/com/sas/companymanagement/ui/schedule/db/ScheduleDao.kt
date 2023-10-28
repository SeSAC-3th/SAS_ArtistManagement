package com.sas.companymanagement.ui.schedule.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sas.companymanagement.ui.schedule.Schedule
/**
*
* @fileName     : ScheduleDao.kt
* @author       : 박지혜, 이종윤
* @since        : 2023-10-27
*/
@Dao
interface ScheduleDao {
    @Insert
    fun insertSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule_tbl WHERE id = :id")
    fun findSchedule(id: Long): Schedule

    @Update
    fun updateSchedule(vararg schedules: Schedule)

    @Query(value = "DELETE FROM schedule_tbl WHERE id = :id")
    fun deleteSchedule(id: Long)

    @Query("SELECT * FROM schedule_tbl")
    fun getAllSchedule(): LiveData<List<Schedule>>

    @Query("SELECT * FROM schedule_tbl WHERE id = :id")
    fun findScheduleById(id: Long): LiveData<Schedule>

}