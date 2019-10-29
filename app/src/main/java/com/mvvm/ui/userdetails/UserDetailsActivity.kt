package com.mvvm.ui.userdetails

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import com.mvvm.BR
import com.mvvm.R
import com.mvvm.databinding.ActivityDetailsBinding
import com.mvvm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_details.*
import java.net.URL
import javax.inject.Inject




class UserDetailsActivity : BaseActivity<ActivityDetailsBinding, UserDetailsViewModel>() {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    var mActivityUserDetailsBinding: ActivityDetailsBinding? = null
    lateinit var userDetailsViewModel: UserDetailsViewModel
    override fun getBindingVariable() = BR.viewModel

    override fun getViewModel(): UserDetailsViewModel {
        userDetailsViewModel= ViewModelProviders.of(this, mViewModelFactory).get(UserDetailsViewModel::class.java)
        return userDetailsViewModel
    }

    override fun getLayoutId()= R.layout.activity_details

    companion object{
        val Result_DATA="delete"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityUserDetailsBinding = getViewDataBinding()
        val bundle = intent.extras
        var url = bundle.getString("URL")
        getUrl(url)

        button.setOnClickListener {
            val sendIntent = Intent(this, UserDetailsActivity::class.java)
            sendIntent.putExtra(Result_DATA, "Your Data");

            // Set the resultCode to Activity.RESULT_OK to
            // indicate a success and attach the Intent
            // which contains our result data
            setResult(RESULT_OK, sendIntent);

            // With finish() we close the SecondActivity to
            // return to FirstActivity
            finish();
        }
        Toast.makeText(this@UserDetailsActivity, "Double tap to zoom in / out ", Toast.LENGTH_SHORT).show()
    }


    fun getUrl(url: String){
        val thread = Thread(Runnable {
            try {
                val newurl = URL(url)
                val mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
                var photoView= mActivityUserDetailsBinding?.photoView
                runOnUiThread {
                    photoView?.setImageBitmap(mIcon_val)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start()
    }
}