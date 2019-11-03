package com.mvvm.repository.models.mapped

class Category{
    var categoryName:String?=null
    var categoryId:Int?=null
    var childCategoryIdList=ArrayList<Int>()
}