package com.mvvm.ui.splash

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.mvvm.BR
import com.mvvm.R
import com.mvvm.databinding.ActivitySplashBinding
import com.mvvm.ui.base.BaseActivity
import com.mvvm.ui.landing.LandingActivity
import javax.inject.Inject
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class SplashActivity: BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    lateinit var splashViewModel: SplashViewModel

    override fun getBindingVariable()= BR.viewModel

    override fun getViewModel(): SplashViewModel {
        splashViewModel= ViewModelProviders.of(this, mViewModelFactory).get(SplashViewModel::class.java)
        return splashViewModel
     }

    override fun getLayoutId()= R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable
            .timer(2000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .map({ o -> openMainActivity() })
            .subscribe()
    }
    private fun openMainActivity() {

        val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }
}