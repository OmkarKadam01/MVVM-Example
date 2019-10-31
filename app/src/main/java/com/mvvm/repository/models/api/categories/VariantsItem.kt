package com.mvvm.repository.models.api.categories

import com.google.gson.annotations.SerializedName

data class VariantsItem(

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("size")
	val size: Any? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
)