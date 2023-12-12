package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class UserActivityResponse(

	@field:SerializedName("data")
	val data: List<DataActivity?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataActivity(

	@field:SerializedName("managedWaste")
	val managedWaste: Int? = null,

	@field:SerializedName("sendWaste")
	val sendWaste: Int? = null,

	@field:SerializedName("point")
	val point: Int? = null
)
