package com.sas.companymanagement.ui.artist.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sas.companymanagement.ui.artist.Artist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ArtistRepository(application: Application) {
    var searchResults = MutableLiveData<List<Artist>>()
    var categoryResults = MutableLiveData<List<Artist>>()
    private var artistDao: ArtistDao
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val allArtists: LiveData<List<Artist>>?

    init {
        val db: ArtistRoomDatabase = ArtistRoomDatabase.getDatabase(application)
        artistDao = db.artistDao()
        allArtists = artistDao.getAllArtist()
    }

    fun insertArtist(newArtist: Artist) {
        coroutineScope.launch(Dispatchers.IO) {
            artistDao.insertArtist(newArtist)
        }
    }

    fun deleteArtist(id: Int) {
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

    fun findArtist(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async artistDao.findArtist(id)
            }.await()
        }
    }


}