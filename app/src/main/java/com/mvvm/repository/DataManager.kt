package com.mvvm.repository

import com.mvvm.repository.local.prefs.PreferencesHelper
import com.mvvm.repository.models.api.Users
import com.mvvm.repository.models.mapped.Categories
import com.mvvm.repository.remote.ApiHelper
import com.mvvm.utils.rx.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class DataManager @Inject constructor(private val apiHelper: ApiHelper,
                                      private val schedulerProvider: SchedulerProvider,
                                      private val sharedPreferences: PreferencesHelper): DataManagerContract
{

    fun getUserData(): Observable<List<Users>> {


        val standingsUrl = "https://api.github.com/users"
        return apiHelper.getUsersData(standingsUrl).observeOn(schedulerProvider.io())
            .subscribeOn(schedulerProvider.io())
            .flatMapIterable { return@flatMapIterable it }
            .map {
                Users().mapFrom(it)
            }
            .toList()
            .toObservable()
    }
    fun getCategoriesData(): Observable<List<Categories>> {


        val standingsUrl = "https://api.github.com/users"
        return apiHelper.getCategoriesData(standingsUrl).observeOn(schedulerProvider.io())
            .subscribeOn(schedulerProvider.io())
            .flatMapIterable { return@flatMapIterable it }
            .map {
                Categories().mapFrom(it)
            }
            .toList()
            .toObservable()
    }
}
