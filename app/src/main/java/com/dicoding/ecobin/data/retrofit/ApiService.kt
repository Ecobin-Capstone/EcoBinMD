package com.dicoding.ecobin.data.retrofit

import com.dicoding.ecobin.data.response.LoginMitraRequest
import com.dicoding.ecobin.data.response.LoginMitraResponse
import com.dicoding.ecobin.data.response.LoginRequest
import com.dicoding.ecobin.data.response.LoginResponse
import com.dicoding.ecobin.data.response.OrganicPartnerResponse
import com.dicoding.ecobin.data.response.OrganicWasteResponse
import com.dicoding.ecobin.data.response.RegisterRequest
import com.dicoding.ecobin.data.response.RegisterResponse
import com.dicoding.ecobin.data.response.SendWasteRequest
import com.dicoding.ecobin.data.response.SendWasteResponse
import com.dicoding.ecobin.data.response.UserActivityResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    @POST("wastepickup/users/{id}/order")
    suspend fun sendWaste(
        @Body requestBody: SendWasteRequest,
        @Path("id") id: Int
    ): Response<SendWasteResponse>
    @GET("wastepickup/list/types/nonorganic")
    suspend fun getListAnorganic(): Response<OrganicWasteResponse>
    @GET("wastepickup/list/types/organic")
    suspend fun getListOrganic(): Response<OrganicWasteResponse>
    @GET("wastepickup/list/partners/organic")
    suspend fun getOrganicPartner(): Response<OrganicPartnerResponse>
    @GET("wastepickup/list/partners/nonorganic")
    suspend fun getNonorganicPartner(): Response<OrganicPartnerResponse>
    @GET("users/{id}/activity")
    suspend fun getActivity(@Path("id") id: String): Response<UserActivityResponse>

}