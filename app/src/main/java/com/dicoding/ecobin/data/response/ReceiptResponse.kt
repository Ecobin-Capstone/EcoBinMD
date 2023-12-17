package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class ReceiptResponse(

	@field:SerializedName("data")
	val data: List<DataReceipt?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataReceipt(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("voucher")
	val voucher: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("exchangeToken")
	val exchangeToken: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
