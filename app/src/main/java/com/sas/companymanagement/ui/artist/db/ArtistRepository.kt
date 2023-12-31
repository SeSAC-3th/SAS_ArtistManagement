package com.sas.companymanagement.ui.artist.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.artist.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
* Please explain the class!!
*
* @fileName             : ArtistRepository
* @auther               : 이기영, 박지혜
* @since                : 2023-10-27
**/
class ArtistRepository(application: Application) {
    var searchResults = MutableLiveData<Artist>()
    private var categoryResults = MutableLiveData<List<Artist>>()
    private var artistDao: ArtistDao
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val allArtists: LiveData<List<Artist>>?

    init {
        val db: ArtistRoomDatabase = ArtistRoomDatabase.getDatabase(application)
        artistDao = db.artistDao()
        allArtists = artistDao.getAllArtist()
    }

    fun getAllArtist(): LiveData<List<Artist>>? {
        return allArtists
    }

    fun getResult(): MutableLiveData<List<Artist>> {
        return categoryResults
    }

    fun insertArtist(newArtist: Artist) {
        coroutineScope.launch(Dispatchers.IO) {
            artistDao.insertArtist(newArtist)
        }
    }

    fun deleteArtist(id: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            artistDao.deleteArtist(id)
        }
    }

    fun findArtistByCategory(category: String) {
        coroutineScope.launch(Dispatchers.Main) {
            categoryResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async artistDao.findArtistByCategory(category)
            }.await()
        }
    }

    fun findArtist(id: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async artistDao.findArtist(id)
            }.await()
        }
    }


    fun findArtistById(id: Long): LiveData<Artist> {
        return artistDao.findArtistById(id)
    }
    fun updateUser(artist: Artist) {
        coroutineScope.launch(Dispatchers.IO) {
            artistDao.updateArtist(artist)
        }
    }
}
