package com.mvvm.repository.models.mapped

import com.mvvm.repository.models.api.categories.CategoriesResponse
import com.mvvm.ui.base.ItemClicked


class Categories () : Mapper<CategoriesResponse,Categories>{

    var productList=ArrayList<Product>()
    var categoryList=ArrayList<Category>()
    var subCategoryList=ArrayList<SubCategory>()
    var listener: ItemClicked? = null
    override fun mapFrom(t: CategoriesResponse): Categories{

        t.categories?.forEach {categories->
            if(!categories?.products.isNullOrEmpty()){
            var subCategory=SubCategory()
                subCategory.subCategoryId=categories?.id
                subCategory.subCategoryName=categories?.name
                subCategoryList.add(subCategory)
            categories?.products?.forEach {productItem->
                val productViewCount: Int?= t.rankings?.get(0)?.products?.filter { s -> s?.id == categories.id  }?.firstOrNull()?.viewCount?:0
                val productOrderCount: Int?= t.rankings?.get(1)?.products?.filter { s -> s?.id == categories.id  }?.firstOrNull()?.orderCount?:0
                val productShareCount: Int?= t.rankings?.get(2)?.products?.filter { s -> s?.id == categories.id  }?.firstOrNull()?.shares?:0
                val product=Product()
                    product.productCategoryName=categories.name?:""
                    product.productCategoryId=categories.id?:0
                    product.productName=productItem?.name?:""
                    product.productId=productItem?.id?:0
                    product.dateAdded=productItem?.dateAdded?:""
                    product.productViewCount=productViewCount?:0
                    product.productOrderCount=productOrderCount?:0
                    product.productShareCount=productShareCount?:0

                productList.add(product)
            }
            }else{
                var category=Category()
                category.categoryId=categories?.id
                category.categoryName=categories?.name
                categories?.childCategories?.forEach{
                    category.childCategoryIdList.add(it?:0)
                }
                categoryList.add(category)
            }
        }
        return this
    }
    fun onItemClicked(){
        listener?.onItemClicked(this)
    }


}