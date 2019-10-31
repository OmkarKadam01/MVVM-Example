package com.mvvm.repository.models.api.categories

import com.google.gson.annotations.SerializedName

data class ProductsItem(

	@field:SerializedName("date_added")
	val dateAdded: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("tax")
	val tax: Tax? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("variants")
	val variants: List<VariantsItem?>? = null
)