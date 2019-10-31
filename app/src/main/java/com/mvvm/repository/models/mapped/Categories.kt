package com.mvvm.repository.models.mapped

import com.mvvm.repository.models.api.categories.CategoriesResponse
import com.mvvm.ui.base.ItemClicked


class Categories () : Mapper<CategoriesResponse, Categories> {
    lateinit var image: String
    lateinit var userName: String
    lateinit var userType: String
    var listener: ItemClicked? = null
    override fun mapFrom(t: CategoriesResponse): Categories {

    /*    image=t.avatarUrl?:""
        userName=t.login?:""
        userType=t.type?:""*/

        return this
    }
    fun onItemClicked(){
        listener?.onItemClicked(this)
    }
}