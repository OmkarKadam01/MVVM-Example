package com.mvvm.ui.userdetails

import com.mvvm.repository.DataManager
import com.mvvm.ui.base.BaseViewModel
import com.mvvm.utils.rx.SchedulerProvider

class UserDetailsViewModel (dataManager: DataManager, schedulerProvider: SchedulerProvider):
    BaseViewModel(dataManager,schedulerProvider) {


}