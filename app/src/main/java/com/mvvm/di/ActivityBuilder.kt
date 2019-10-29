package com.mvvm.di

import com.mvvm.di.scopes.PerActivity
import com.mvvm.ui.landing.LandingActivity
import com.mvvm.ui.landing.LandingActivityModule
import com.mvvm.ui.splash.SplashActivity
import com.mvvm.ui.splash.SplashActivityModule
import com.mvvm.ui.userdetails.UserDetailsActivity
import com.mvvm.ui.userdetails.UserDetailsActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = arrayOf(
        LandingActivityModule::class
    ))
    @PerActivity
    abstract fun bindLandingActivity(): LandingActivity


    @ContributesAndroidInjector(modules = arrayOf(SplashActivityModule::class))
    internal abstract fun bindActivity(): SplashActivity

    @ContributesAndroidInjector(modules = arrayOf(UserDetailsActivityModule::class))
    internal abstract fun bindUserDetailActivity(): UserDetailsActivity
}