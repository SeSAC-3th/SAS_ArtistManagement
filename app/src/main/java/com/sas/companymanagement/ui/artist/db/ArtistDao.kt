package com.sas.companymanagement.ui.artist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sas.companymanagement.ui.artist.Artist


/**
* Please explain the class!!
*
* @fileName             : ArtistDao
* @auther               : 이기영, 박지혜, 이종윤
* @since                : 2023-10-18
**/
@Dao
interface ArtistDao {
    @Insert
    fun insertArtist(artist: Artist)

    @Query("SELECT * FROM artist_tbl WHERE id = :id")
    fun findArtist(id: Long): Artist

    @Update
    fun updateArtist(vararg artists: Artist)

    @Query(value = "DELETE FROM artist_tbl WHERE id = :id")
    fun deleteArtist(id: Long)

    @Query("SELECT * FROM artist_tbl WHERE artistCategory = :category")
    fun findArtistByCategory(category: String): List<Artist>


    @Query("SELECT * FROM artist_tbl")
    fun getAllArtist(): LiveData<List<Artist>>

    @Query("SELECT * FROM artist_tbl WHERE id = :id")
    fun findArtistById(id: Long): LiveData<Artist>
}