package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.data.response.OrganicPartnerResponse
import com.dicoding.ecobin.data.response.OrganicWasteResponse

class ListWasteViewModel (private val repository: WasteRepository) : ViewModel() {
    suspend fun getOrganicWaste(): OrganicWasteResponse {
        return repository.getOrganicWaste()
    }
    suspend fun getOrganicPartner(): OrganicPartnerResponse {
        return repository.getOrganicPartner()
    }
}