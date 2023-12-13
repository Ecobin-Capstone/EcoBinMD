package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class SendWasteResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("estimatePointsEarned")
	val estimatePointsEarned: Int? = null
)

data class SendWasteRequest(
	val partnersId: Int,
	val phoneNumber: String,
	val province: String,
	val subDistrict: String,
	val village: String,
	val postalCode: String,
	val latitude: Double,
	val longitude: Double,
	val address: String,
	val date: String,
	val time: String,
	val note: String,
	val wasteItems: List<WasteItem>
)

data class WasteItem(
	val typeId: Int,
	val quantity: Int
)
