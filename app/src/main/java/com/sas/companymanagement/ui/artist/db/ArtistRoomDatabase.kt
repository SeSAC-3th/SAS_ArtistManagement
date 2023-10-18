package com.sas.companymanagement.ui.artist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sas.companymanagement.ui.artist.Artist

@Database(entities = [(Artist::class)], exportSchema = false, version = 1)
abstract class ArtistRoomDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    companion object {
        private lateinit var INSTANCE: ArtistRoomDatabase
        internal fun getDatabase(context: Context): ArtistRoomDatabase {
            if (!this::INSTANCE.isInitialized) {
                synchronized(ArtistRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ArtistRoomDatabase::class.java,
                        "artist_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}