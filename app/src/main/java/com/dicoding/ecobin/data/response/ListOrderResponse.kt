package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class ListOrderResponse(

	@field:SerializedName("data")
	val data: List<DataOrder?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataOrder(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("wasteItems")
	val wasteItems: List<WasteItems?>? = null,

	@field:SerializedName("postalCode")
	val postalCode: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("pickupId")
	val pickupId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("village")
	val village: String? = null,

	@field:SerializedName("subDistrict")
	val subDistrict: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)

data class WasteItems(

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
)
