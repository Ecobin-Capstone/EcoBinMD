package com.dicoding.ecobin.data.repository

import com.dicoding.ecobin.data.response.LoginMitraRequest
import com.dicoding.ecobin.data.response.LoginMitraResponse
import com.dicoding.ecobin.data.response.LoginRequest
import com.dicoding.ecobin.data.response.LoginResponse
import com.dicoding.ecobin.data.response.ReceiptResponse
import com.dicoding.ecobin.data.response.RegisterRequest
import com.dicoding.ecobin.data.response.RegisterResponse
import com.dicoding.ecobin.data.response.UserActivityResponse
import com.dicoding.ecobin.data.retrofit.ApiService
import com.dicoding.ecobin.pref.UserModel
import com.dicoding.ecobin.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun getReceipt(id: String): ReceiptResponse {
        try {
            val successResponse = apiService.getReceipt(id)
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return ReceiptResponse(message = "Response body is null")
                }
            } else {
                return ReceiptResponse(message = "Request failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ReceiptResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return ReceiptResponse(message = "Network error: ${e.message}")
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun registerUser(name: String,phone: String, email: String, password: String, latitude: Double?, longitude: Double?): RegisterResponse {
        try {
            val request = RegisterRequest(
                name = name,
                email = email,
                phoneNumber = phone,
                password = password,
                latitude = latitude,
                longitude = longitude
            )
            val successResponse = apiService.register(request)
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return RegisterResponse(message = "Response body is null")
                }
            } else {
                return RegisterResponse(message = "Registration failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return RegisterResponse(message = "Network error: ${e.message}")
        }
    }
    suspend fun loginUser(email: String, password: String): LoginResponse {
        try {
            val request = LoginRequest(
                email = email,
                password = password
            )
            val successResponse = apiService.login(request)
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return LoginResponse(message = "Response body is null")
                }
            } else {
                return LoginResponse(message = "Login failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return LoginResponse(message = "Network error: ${e.message}")
        }
    }

    suspend fun loginMitra(email: String, password: String): LoginMitraResponse {
        try {
            val request = LoginMitraRequest(
                email = email,
                password = password
            )
            val successResponse = apiService.loginMitra(request)
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return LoginMitraResponse(message = "Response body is null")
                }
            } else {
                return LoginMitraResponse(message = "Login failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginMitraResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return LoginMitraResponse(message = "Network error: ${e.message}")
        }
    }
    suspend fun getActivity(id: String): UserActivityResponse{
        try {
            val successResponse = apiService.getActivity(id)
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return UserActivityResponse(message = "Response body is null")
                }
            } else {
                return UserActivityResponse(message = "Request failed with HTTP status code: ${successResponse.code()}")
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, UserActivityResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return UserActivityResponse(message = "Network error: ${e.message}")
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference,apiService)
            }.also { instance = it }
    }
}