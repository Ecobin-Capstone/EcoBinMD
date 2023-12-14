package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class AcceptDeclineResponse(

	@field:SerializedName("message")
	val message: String? = null
)

data class OrderDataToUpdate(
	val pickupId: Int,
)