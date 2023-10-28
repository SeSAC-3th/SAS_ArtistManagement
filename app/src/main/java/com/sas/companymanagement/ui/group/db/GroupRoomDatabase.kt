package com.sas.companymanagement.ui.group.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sas.companymanagement.ui.group.Group

/**
* GroupRoomDatabase
*
* @fileName         : GroupRoomDatabase
* @author           : 이기영
* @Since            : 2023-10-27
*/
@Database(entities = [(Group::class)], exportSchema = false, version = 1)
abstract class GroupRoomDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    companion object {
        private lateinit var INSTANCE: GroupRoomDatabase
        internal fun getDatabase(context: Context): GroupRoomDatabase {
            if (!this::INSTANCE.isInitialized) {
                synchronized(GroupRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GroupRoomDatabase::class.java,
                        "group_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}