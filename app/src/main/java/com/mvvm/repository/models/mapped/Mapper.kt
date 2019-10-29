package com.mvvm.repository.models.mapped

interface Mapper <T,V>{
    fun mapFrom(t:T):V
}