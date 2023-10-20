package com.sas.companymanagement.ui.artist

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sas.companymanagement.ui.schedule.Schedule


@Entity(tableName = "artist_tbl")
data class Artist(
    @ColumnInfo(name = "artistName")
    var artistName: String = "",

    @ColumnInfo(name = "artistNickname")
    var artistNickname: String = "",

    @ColumnInfo(name = "artistImage")
    var artistImage: String = "",

    @ColumnInfo(name = "artistCategory")
    var artistCategory: String = "",

    @ColumnInfo(name = "artistBirth")
    var artistBirth: String = "",

    @ColumnInfo(name = "artistGender")
    var artistGender: String = "",

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,
)
