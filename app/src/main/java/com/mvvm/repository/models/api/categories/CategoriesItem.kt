package com.mvvm.repository.models.api.categories

import com.google.gson.annotations.SerializedName

data class CategoriesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("child_categories")
	val childCategories: List<Int?>? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
)