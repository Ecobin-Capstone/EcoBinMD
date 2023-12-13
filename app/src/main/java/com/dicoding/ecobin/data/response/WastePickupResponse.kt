package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class WastePickupResponse(

	@field:SerializedName("data")
	val data: List<DataPickup?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataPickup(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("wasteItems")
	val wasteItems: List<WasteItemsItem?>? = null,

	@field:SerializedName("postalCode")
	val postalCode: String? = null,

	@field:SerializedName("pickupId")
	val pickupId: Int? = null,

	@field:SerializedName("timeAgo")
	val timeAgo: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("partner")
	val partner: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("village")
	val village: String? = null,

	@field:SerializedName("subDistrict")
	val subDistrict: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class WasteItemsItem(

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
)
