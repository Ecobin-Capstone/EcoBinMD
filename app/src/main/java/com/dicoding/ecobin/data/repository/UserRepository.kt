package com.dicoding.ecobin.data.repository

import com.dicoding.ecobin.data.response.LoginResponse
import com.dicoding.ecobin.data.response.RegisterRequest
import com.dicoding.ecobin.data.response.RegisterResponse
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

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun registerUser(name: String,phone: String, email: String, password: String): RegisterResponse {
        try {
            val request = RegisterRequest(
                name = name,
                email = email,
                phoneNumber = phone,
                password = password
            )
//            val response = yourApiService.register(request)
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
            val successResponse = apiService.login(email, password)
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