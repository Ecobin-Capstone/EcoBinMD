package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.data.response.OrganicPartnerResponse
import com.dicoding.ecobin.data.response.OrganicWasteResponse
import com.dicoding.ecobin.data.response.SendWasteResponse
import com.dicoding.ecobin.data.response.WasteItem

class ListWasteViewModel (private val repository: WasteRepository) : ViewModel() {
    suspend fun getOrganicWaste(): OrganicWasteResponse {
        return repository.getOrganicWaste()
    }
    suspend fun getNonOrganicWaste(): OrganicWasteResponse {
        return repository.getnonOrganicWaste()
    }
    suspend fun getOrganicPartner(): OrganicPartnerResponse {
        return repository.getOrganicPartner()
    }

    suspend fun getNonOrganicPartner(): OrganicPartnerResponse {
        return repository.getnonOrganicPartner()
    }

    suspend fun sendWaste(id: Int, partnersIdValue: Int, phoneNumberValue: String, provinceValue: String, subDistrictValue: String, villageValue: String, postalCodeValue: String, latitudeValue: Double, longitudeValue: Double, addressValue: String, dateValue: String, timeValue: String, noteValue: String, wasteItemsList: List<WasteItem>): SendWasteResponse{
        return repository.sendWaste(id,partnersIdValue, phoneNumberValue, provinceValue, subDistrictValue, villageValue, postalCodeValue, latitudeValue, longitudeValue, addressValue, dateValue, timeValue, noteValue, wasteItemsList )
    }
}