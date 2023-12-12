package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class NonorganicPartnerResponse(

	@field:SerializedName("data")
	val data: List<DataNon?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataNon(

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("village")
	val village: String? = null,

	@field:SerializedName("subDistrict")
	val subDistrict: String? = null
)
