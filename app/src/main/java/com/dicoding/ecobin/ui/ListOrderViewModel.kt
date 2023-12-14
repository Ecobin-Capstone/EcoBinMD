package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.data.response.AcceptDeclineResponse
import com.dicoding.ecobin.data.response.ListOrderResponse

class ListOrderViewModel (private val repository: WasteRepository) : ViewModel() {
    suspend fun getOrder(id: String): ListOrderResponse {
        return repository.getOrder(id)
    }
    suspend fun updateOrder(id: String, pickupID:Int): AcceptDeclineResponse {
        return repository.updateOrder(id,pickupID)
    }

    suspend fun declineOrder(id: String, pickupID:Int): AcceptDeclineResponse {
        return repository.declineOrder(id,pickupID)
    }
}