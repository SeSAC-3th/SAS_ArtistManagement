package com.sas.companymanagement.ui.artist.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sas.companymanagement.ui.artist.Artist

@Dao
interface ArtistDao {
    @Insert
    fun insertArtist(artist: Artist)
//    @Query("SELECT artistName, artistImage FROM artist_tbl WHERE artistId = :id")
//    fun findArtist(id: Int): List<Artist>

    @Query("SELECT * FROM artist_tbl WHERE artistId = :id")
    fun findArtist(id: Int): List<Artist>

    @Update
    fun updateArtist(vararg artists: Artist)

    @Query(value = "DELETE FROM artist_tbl WHERE artistId = :id")
    fun deleteArtist(id: Int)

    @Query("SELECT * FROM artist_tbl WHERE artistCategory = :category")
    fun findArtistByCategory(category: String): List<Artist>


    @Query("SELECT * FROM artist_tbl")
    fun getAllArtist(): LiveData<List<Artist>>
}