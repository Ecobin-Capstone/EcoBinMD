package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("message")
	val message: String? = null
)

data class UpdateData(
	val name: String,
	val email: String,
	val phoneNumber: String,
)