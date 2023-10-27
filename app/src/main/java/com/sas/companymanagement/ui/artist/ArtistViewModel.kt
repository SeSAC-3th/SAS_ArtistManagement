package com.sas.companymanagement.ui.artist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sas.companymanagement.ui.artist.db.ArtistRepository

class ArtistViewModel(application: Application) : AndroidViewModel(application){
    private val repository: ArtistRepository = ArtistRepository(application)
    val allArtists: LiveData<List<Artist>>? = repository.getAllArtist()
    var categoryResults: LiveData<List<Artist>> = repository.getResult()

    fun findArtistByCategory(category: String) {
        repository.findArtistByCategory(category)
    }

    fun getAllArtist() {
        repository.getAllArtist()
    }
}