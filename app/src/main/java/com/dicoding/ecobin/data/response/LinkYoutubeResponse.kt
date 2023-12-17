package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class LinkYoutubeResponse(

	@field:SerializedName("dataYT")
	val data: List<dataYT?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class YoutubeRequest(
	val wasteType: String
)
data class dataYT(

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
