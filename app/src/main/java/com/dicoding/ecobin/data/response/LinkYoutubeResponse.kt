package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class LinkYoutubeResponse(

	@field:SerializedName("data")
	val data: List<DataYT?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class YoutubeRequest(
	val wasteType: String
)
data class DataYT(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("videoUrl")
	val videoUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
