package com.dicoding.ecobin.data.response

import com.google.gson.annotations.SerializedName

data class ClassifierResponse(

	@field:SerializedName("predictions")
	val predictions: List<PredictionsItem?>? = null
)

data class PredictionsItem(

	@field:SerializedName("metal")
	val metal: Any? = null,

	@field:SerializedName("plastic")
	val plastic: Any? = null,

	@field:SerializedName("glass")
	val glass: Any? = null,

	@field:SerializedName("biodegradable")
	val biodegradable: Any? = null,

	@field:SerializedName("cardboard")
	val cardboard: Any? = null,

	@field:SerializedName("paper")
	val paper: Any? = null
)
