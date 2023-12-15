package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class RedeemResponse(

	@field:SerializedName("data")
	val data: List<DataRedeem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)
 data class RedeemPointRequest(
	 val vouchersId: Int,
 )
data class DataRedeem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("voucher")
	val voucher: String? = null,

	@field:SerializedName("exchangeToken")
	val exchangeToken: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("currentPoint")
	val currentPoint: Int? = null
)
