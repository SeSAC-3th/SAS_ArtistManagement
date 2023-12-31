package com.sas.companymanagement.ui.schedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sas.companymanagement.ui.schedule.db.ScheduleRepository
import java.text.SimpleDateFormat
import java.util.Locale
/**
 * Schedule Tab
 *
 * @fileName             : ScheduleViewModel
 * @auther               : 박지혜
 * @since                : 2023-10-17
 **/
class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ScheduleRepository = ScheduleRepository(application)
    val allSchedules: LiveData<List<Schedule>>? = repository.allSchedules
    private val _selectedSchedule = MutableLiveData<List<Schedule>?>(emptyList())
    val selectedSchedule: LiveData<List<Schedule>?> get() = _selectedSchedule

    /**
     * 캘린더에서 스케쥴 선택시 해당 날짜 스케쥴 보여주기
     *
     * @author 박지혜
     */
    fun getSelectedSchedule(selectedDay: CalendarDay) {
        val selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
            selectedDay.toString().substring(12).removeSuffix("}")
        )
        val newSchedules = allSchedules?.value?.filter { schedule ->
            val scheduleDate = SimpleDateFormat(
                "yyyy년 MM월 dd일",
                Locale.getDefault()
            ).parse(schedule.scheduleDateBefore.substring(0, 13))
            scheduleDate == selectedDate
        }
        _selectedSchedule.postValue(newSchedules)
    }

    fun updateSchedule(schedule: Schedule) {
        repository.updateSchedule(schedule)
    }
}