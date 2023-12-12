package com.dicoding.ecobin.data.retrofit

import com.dicoding.ecobin.data.response.LoginMitraRequest
import com.dicoding.ecobin.data.response.LoginMitraResponse
import com.dicoding.ecobin.data.response.LoginRequest
import com.dicoding.ecobin.data.response.LoginResponse
import com.dicoding.ecobin.data.response.RegisterRequest
import com.dicoding.ecobin.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/register")
    suspend fun register(
        @Body requestBody: RegisterRequest
    ): Response<RegisterResponse>
    @POST("users/login")
    suspend fun login(
        @Body requestBody: LoginRequest
    ): Response<LoginResponse>
    @POST("partners/login")
    suspend fun loginMitra(
        @Body requestBody: LoginMitraRequest
    ): Response<LoginMitraResponse>

}