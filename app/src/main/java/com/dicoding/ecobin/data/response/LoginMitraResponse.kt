package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class LoginMitraResponse(

	@field:SerializedName("data")
	val data: List<DataItemMitra?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class LoginMitraRequest(
	val email: String,
	val password: String
)

data class DataItemMitra(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("postalCode")
	val postalCode: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("village")
	val village: String? = null,

	@field:SerializedName("subDistrict")
	val subDistrict: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

)
