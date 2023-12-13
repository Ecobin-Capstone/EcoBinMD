package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.data.response.ListOrderResponse

class ListOrderViewModel (private val repository: WasteRepository) : ViewModel() {
    suspend fun getOrder(id: String): ListOrderResponse {
        return repository.getOrder(id)
    }
}