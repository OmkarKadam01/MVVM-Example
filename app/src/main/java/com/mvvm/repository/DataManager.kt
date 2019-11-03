package com.mvvm.repository

import com.mvvm.repository.local.prefs.PreferencesHelper
import com.mvvm.repository.models.mapped.Categories
import com.mvvm.repository.remote.ApiHelper
import com.mvvm.utils.rx.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class DataManager @Inject constructor(private val apiHelper: ApiHelper,
                                      private val schedulerProvider: SchedulerProvider,
                                      private val sharedPreferences: PreferencesHelper): DataManagerContract
{


    fun getCategoriesData(): Observable<Categories> {


        val standingsUrl = "https://stark-spire-93433.herokuapp.com/json"
        return apiHelper.getCategoriesData(standingsUrl).observeOn(schedulerProvider.io())
            .subscribeOn(schedulerProvider.io())
            .map {
                Categories().mapFrom(it)
            }
    }
}
