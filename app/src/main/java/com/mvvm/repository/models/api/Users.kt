package com.mvvm.repository.models.api

import com.mvvm.repository.models.mapped.Mapper
import com.mvvm.ui.base.ItemClicked
import com.mvvm.ui.landing.pojo.UserResponse

class Users () : Mapper< UserResponse, Users> {
    lateinit var image: String
    lateinit var userName: String
    lateinit var userType: String
    var listener: ItemClicked? = null
    override fun mapFrom(t: UserResponse): Users {

        image=t.avatarUrl?:""
        userName=t.login?:""
        userType=t.type?:""

        return this
    }
    fun onItemClicked(){
        listener?.onItemClicked(this)
    }
}