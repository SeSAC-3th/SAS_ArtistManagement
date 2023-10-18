package com.sas.companymanagement.ui.artist

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "artist_tbl")
class Artist {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "artistId")
    var id: Int = 0

    @ColumnInfo(name = "artistName")
    var artistName: String? = null

    @ColumnInfo(name = "artistImage")
    var artistImage: String? = null

    @ColumnInfo(name = "artistCategory")
    var artistCategory: String? = null
    constructor() {}
    @Ignore
    constructor(id: Int, artistName: String, artistImage: String) {
        this.id = id
        this.artistName = artistName
        this.artistImage = artistImage
    }
    @Ignore
    constructor(artistName: String, artistImage: String) {
        this.artistName = artistName
        this.artistImage = artistImage
    }
}