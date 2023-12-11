package com.dicoding.ecobin.data.repository

import com.dicoding.ecobin.data.retrofit.ApiService
import com.dicoding.ecobin.pref.UserModel
import com.dicoding.ecobin.pref.UserPreference
import kotlinx.coroutines.flow.Flow

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

//    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
//        try {
//            val successResponse = apiService.register(name, email, password)
//            if (successResponse.isSuccessful) {
//                val responseBody = successResponse.body()
//                if (responseBody != null) {
//                    return responseBody
//                } else {
//                    return RegisterResponse(error = true, message = "Response body is null")
//                }
//            } else {
//                return RegisterResponse(error = true, message = "Registration failed with HTTP status code: ${successResponse.code()}")
//            }
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
//            return errorResponse
//        } catch (e: Exception) {
//            return RegisterResponse(error = true, message = "Network error: ${e.message}")
//        }
//    }
//    suspend fun loginUser(email: String, password: String): LoginResponse {
//        try {
//            val successResponse = apiService.login(email, password)
//            if (successResponse.isSuccessful) {
//                val responseBody = successResponse.body()
//                if (responseBody != null) {
//                    return responseBody
//                } else {
//                    return LoginResponse(error = true, message = "Response body is null")
//                }
//            } else {
//                return LoginResponse(error = true, message = "Login failed with HTTP status code: ${successResponse.code()}")
//            }
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
//            return errorResponse
//        } catch (e: Exception) {
//            return LoginResponse(error = true, message = "Network error: ${e.message}")
//        }
//    }
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