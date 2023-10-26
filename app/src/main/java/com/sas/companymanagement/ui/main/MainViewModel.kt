package com.sas.companymanagement.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistRepository
import com.sas.companymanagement.ui.schedule.Schedule
import com.sas.companymanagement.ui.schedule.db.ScheduleRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val artistRepository: ArtistRepository = ArtistRepository(application)
    val allArtists: LiveData<List<Artist>>? = artistRepository.getAllArtist()

    private val scheduleRepository: ScheduleRepository = ScheduleRepository(application)
    val allSchedules: LiveData<List<Schedule>>? = scheduleRepository.allSchedules
}