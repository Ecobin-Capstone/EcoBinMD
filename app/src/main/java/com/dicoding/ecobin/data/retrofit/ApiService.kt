package com.dicoding.ecobin.data.retrofit

import com.dicoding.ecobin.data.response.AcceptDeclineResponse
import com.dicoding.ecobin.data.response.ListOrderResponse
import com.dicoding.ecobin.data.response.LoginMitraRequest
import com.dicoding.ecobin.data.response.LoginMitraResponse
import com.dicoding.ecobin.data.response.LoginRequest
import com.dicoding.ecobin.data.response.LoginResponse
import com.dicoding.ecobin.data.response.OrderDataToUpdate
import com.dicoding.ecobin.data.response.OrganicPartnerResponse
import com.dicoding.ecobin.data.response.OrganicWasteResponse
import com.dicoding.ecobin.data.response.ProfileResponse
import com.dicoding.ecobin.data.response.RedeemPointRequest
import com.dicoding.ecobin.data.response.RedeemResponse
import com.dicoding.ecobin.data.response.RegisterRequest
import com.dicoding.ecobin.data.response.RegisterResponse
import com.dicoding.ecobin.data.response.SendWasteRequest
import com.dicoding.ecobin.data.response.SendWasteResponse
import com.dicoding.ecobin.data.response.UpdateData
import com.dicoding.ecobin.data.response.UserActivityResponse
import com.dicoding.ecobin.data.response.VoucherResponse
import com.dicoding.ecobin.data.response.WastePickupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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
    @POST("vouchers/users/{id}/redeem")
    suspend fun redeemPoint(
        @Body requestBody: RedeemPointRequest,
        @Path("id") id: Int
    ): Response<RedeemResponse>
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

    @GET("wastepickup/users/{id}/history/pending")
    suspend fun getPending(@Path("id") id: String): Response<WastePickupResponse>
    @GET("wastepickup/users/{id}/history/accept")
    suspend fun getAccept(@Path("id") id: String): Response<WastePickupResponse>
    @GET("wastepickup/users/{id}/history/decline")
    suspend fun getDecline(@Path("id") id: String): Response<WastePickupResponse>

    @GET("partners/{id}/list/orders")
    suspend fun getOrder(@Path("id") id: String): Response<ListOrderResponse>

    @GET("vouchers/list")
    suspend fun getVoucher(): Response<VoucherResponse>

    @PATCH("partners/{id}/accept")
    suspend fun updateOrder(
        @Path("id") id: String,
        @Body orderData: OrderDataToUpdate
    ): Response<AcceptDeclineResponse>
    @PATCH("partners/{id}/decline")
    suspend fun declineOrder(
        @Path("id") id: String,
        @Body orderData: OrderDataToUpdate
    ): Response<AcceptDeclineResponse>

    @PATCH("users/{id}/editprofile")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body updateData: UpdateData
    ): Response<ProfileResponse>


}