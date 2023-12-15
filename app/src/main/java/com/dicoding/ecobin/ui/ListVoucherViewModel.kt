package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.data.response.RedeemResponse
import com.dicoding.ecobin.data.response.VoucherResponse

class ListVoucherViewModel(private val repository: WasteRepository) : ViewModel() {
    suspend fun getVoucher(): VoucherResponse {
        return repository.getVoucher()
    }

    suspend fun redeemPoint(vouchersId : Int, id: Int): RedeemResponse {
        return repository.redeemPoint(id,vouchersId)
    }
}