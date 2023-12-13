package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("message")
	val message: String? = null
)
data class RegisterRequest(
	val name: String,
	val email: String,
	val phoneNumber: String,
	val password: String,
	val latitude : Double?,
	val longitude: Double?
)