package com.mvvm.repository.models.api.categories

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(

	@field:SerializedName("rankings")
	val rankings: List<RankingsItem?>? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null
)