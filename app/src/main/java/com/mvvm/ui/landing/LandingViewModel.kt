package com.mvvm.ui.landing

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.util.Log
import com.mvvm.repository.DataManager
import com.mvvm.repository.models.api.Users
import com.mvvm.ui.base.BaseViewModel
import com.mvvm.utils.rx.SchedulerProvider
import timber.log.Timber

class LandingViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider)
                      : BaseViewModel(dataManager,schedulerProvider),LifecycleObserver{
    val standingsLiveData: MutableLiveData<List<Users?>> by lazy { MutableLiveData<List<Users?>>() }

    val standingsArrayList: ObservableList<Users> = ObservableArrayList<Users>() as ObservableList<Users>

    fun updateStandingsList(listOfAllMatches: List<Users?>?) {

        listOfAllMatches?.let {
            standingsArrayList.clear()
            standingsArrayList.addAll(it)
        }

    }

    fun fetchUserData() {
        dataManager.getUserData()?.subscribeOn(schedulerProvider.io())?.observeOn(schedulerProvider.ui())?.subscribe({
            standingsLiveData.value = it
            Log.e("Android",it.toString())
        }) {
            // handleError(it)
            Timber.e(it)
        }?.let {
            compositeDisposable.add(
                it
            )
        }

    }

}