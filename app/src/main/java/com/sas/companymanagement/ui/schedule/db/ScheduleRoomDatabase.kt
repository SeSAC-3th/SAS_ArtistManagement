package com.sas.companymanagement.ui.schedule.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sas.companymanagement.ui.artist.Artist
import com.sas.companymanagement.ui.artist.db.ArtistDao
import com.sas.companymanagement.ui.schedule.Schedule

@Database(entities = [(Schedule::class)], exportSchema = false, version = 1)
abstract class ScheduleRoomDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        private lateinit var INSTANCE: ScheduleRoomDatabase
        internal fun getDatabase(context: Context): ScheduleRoomDatabase {
            if (!this::INSTANCE.isInitialized) {
                synchronized(ScheduleRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ScheduleRoomDatabase::class.java,
                        "schedule_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}

