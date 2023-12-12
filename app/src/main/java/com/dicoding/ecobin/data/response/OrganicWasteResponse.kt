package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class OrganicWasteResponse(

	@field:SerializedName("data")
	val data: List<DataOrganicWaste?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataOrganicWaste(

	@field:SerializedName("pointKg")
	val pointKg: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("totalKg")
	val totalKg: Int? = null
)
