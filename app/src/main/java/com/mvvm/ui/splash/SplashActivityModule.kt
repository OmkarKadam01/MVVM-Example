package com.mvvm.ui.splash

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.FragmentManager
import com.mvvm.repository.DataManager
import com.mvvm.ui.ViewModelFactory
import com.mvvm.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides


@Module
class SplashActivityModule {



    @Provides
    fun mainViewModelProvider(splashViewModel: SplashViewModel): ViewModelProvider.Factory {
        return ViewModelFactory(splashViewModel)
    }

    @Provides
    fun provideMainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider): SplashViewModel {
        return SplashViewModel(dataManager, schedulerProvider)
    }



}