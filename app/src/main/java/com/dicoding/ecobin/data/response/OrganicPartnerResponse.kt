package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class OrganicPartnerResponse(

	@field:SerializedName("data")
	val data: List<DataPartner?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataPartner(

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
