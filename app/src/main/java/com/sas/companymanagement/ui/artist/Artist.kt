package com.sas.companymanagement.ui.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @fileName             : Artist
 * @auther               : 이기영, 박지혜
 * @since                : 2023-10-18
 **/
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

    @ColumnInfo(name = "artistEval")
    var artistEval: String = "",

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,
)
