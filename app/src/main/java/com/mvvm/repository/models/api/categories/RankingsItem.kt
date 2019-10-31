package com.mvvm.repository.models.api.categories

import com.google.gson.annotations.SerializedName

data class RankingsItem(

	@field:SerializedName("ranking")
	val ranking: String? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
)