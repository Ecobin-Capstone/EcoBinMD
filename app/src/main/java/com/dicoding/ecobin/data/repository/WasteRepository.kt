package com.dicoding.ecobin.data.repository

import com.dicoding.ecobin.data.response.OrganicPartnerResponse
import com.dicoding.ecobin.data.response.OrganicWasteResponse
import com.dicoding.ecobin.data.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException

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