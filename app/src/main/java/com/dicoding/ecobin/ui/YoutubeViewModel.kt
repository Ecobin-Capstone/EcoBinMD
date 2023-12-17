package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.WasteRepository

class YoutubeViewModel (private val repository: WasteRepository) : ViewModel() {
    suspend fun linkYoutube(wasteType:String)= repository.linkYoutube(wasteType)
}