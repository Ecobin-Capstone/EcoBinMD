package com.dicoding.ecobin.data.repository

import com.dicoding.ecobin.data.response.OrganicPartnerResponse
import com.dicoding.ecobin.data.response.OrganicWasteResponse
import com.dicoding.ecobin.data.response.SendWasteRequest
import com.dicoding.ecobin.data.response.SendWasteResponse
import com.dicoding.ecobin.data.response.WasteItem
import com.dicoding.ecobin.data.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException
import java.sql.Time
import java.util.Date

class WasteRepository private constructor(
    private val apiService: ApiService
){
    suspend fun getOrganicWaste(): OrganicWasteResponse {
        try {
            val successResponse = apiService.getListOrganic()
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return OrganicWasteResponse(message = "Response body is null")
                }
            } else {
                return OrganicWasteResponse(message = "Request failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, OrganicWasteResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return OrganicWasteResponse(message = "Network error: ${e.message}")
        }
    }

    suspend fun sendWaste(id: Int,partnersIdValue: Int,phoneNumberValue: String, provinceValue: String, subDistrictValue: String, villageValue: String, postalCodeValue: String, latitudeValue: Double, longitudeValue: Double, addressValue: String, dateValue: String, timeValue: String, noteValue: String, wasteItemsList: List<WasteItem> ): SendWasteResponse {
        try {
            val request = SendWasteRequest(
                partnersId = partnersIdValue,
                phoneNumber = phoneNumberValue,
                province = provinceValue,
                subDistrict = subDistrictValue,
                village = villageValue,
                postalCode = postalCodeValue,
                latitude = latitudeValue,
                longitude = longitudeValue,
                address = addressValue,
                date = dateValue,
                time = timeValue,
                note = noteValue,
                wasteItems = wasteItemsList
            )

            val successResponse = apiService.sendWaste(request,id)
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return SendWasteResponse(message = "Response body is null")
                }
            } else {
                return SendWasteResponse(message = "Request failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SendWasteResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return SendWasteResponse(message = "Network error: ${e.message}")
        }
    }

    suspend fun getnonOrganicWaste(): OrganicWasteResponse {
        try {
            val successResponse = apiService.getListAnorganic()
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return OrganicWasteResponse(message = "Response body is null")
                }
            } else {
                return OrganicWasteResponse(message = "Request failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, OrganicWasteResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return OrganicWasteResponse(message = "Network error: ${e.message}")
        }
    }

    suspend fun getOrganicPartner(): OrganicPartnerResponse {
        try {
            val successResponse = apiService.getOrganicPartner()
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return OrganicPartnerResponse(message = "Response body is null")
                }
            } else {
                return OrganicPartnerResponse(message = "Request failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, OrganicPartnerResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return OrganicPartnerResponse(message = "Network error: ${e.message}")
        }
    }

    suspend fun getnonOrganicPartner(): OrganicPartnerResponse {
        try {
            val successResponse = apiService.getNonorganicPartner()
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return OrganicPartnerResponse(message = "Response body is null")
                }
            } else {
                return OrganicPartnerResponse(message = "Request failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, OrganicPartnerResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return OrganicPartnerResponse(message = "Network error: ${e.message}")
        }
    }
    companion object {
        @Volatile
        private var instance: WasteRepository? = null
        fun getInstance(
            apiService: ApiService
        ): WasteRepository =
            instance ?: synchronized(this) {
                instance ?: WasteRepository(apiService)
            }.also { instance = it }
    }
}