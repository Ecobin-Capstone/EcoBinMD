package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class VoucherResponse(

	@field:SerializedName("data")
	val data: List<DataVoucher?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataVoucher(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("point")
	val point: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
