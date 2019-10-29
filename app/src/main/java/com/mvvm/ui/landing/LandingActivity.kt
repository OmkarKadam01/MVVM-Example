package com.mvvm.ui.landing

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.BindingAdapter
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvvm.BR
import com.mvvm.R
import com.mvvm.databinding.ActivityLandingBinding
import com.mvvm.repository.models.api.Users
import com.mvvm.ui.base.BaseActivity
import com.mvvm.ui.base.ItemClicked
import com.mvvm.ui.landing.adapter.LandingAdapter
import com.mvvm.ui.userdetails.UserDetailsActivity
import javax.inject.Inject




@BindingAdapter("bind:adapter")
fun observeStandingList(recyclerView: RecyclerView, standingList: List<Users>) {
    val adapter = recyclerView.adapter as? LandingAdapter
    adapter?.clearAllItems()
    adapter?.addAllItems(standingList)
}

@BindingAdapter("bind:imageUrl")
fun setCommentUrl(imageView: ImageView, url: String) {
    val context = imageView.context
    Glide.with(context).load(url).placeholder(R.drawable.user_icon).apply(RequestOptions.circleCropTransform()).into(imageView)
}
class LandingActivity: BaseActivity<ActivityLandingBinding, LandingViewModel>(), ItemClicked {


    @Inject
    lateinit var viewModelFactory:  ViewModelProvider.Factory

    lateinit var landingViewModel: LandingViewModel
    var mActivityLandingBinding: ActivityLandingBinding? = null
    @Inject
    lateinit var standingsAdapter: LandingAdapter
    @Inject
    lateinit var layoutManager: LinearLayoutManager
    var ASK_QUESTION_REQUEST = 1
    var lastClickedUser=Users()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityLandingBinding = getViewDataBinding()
        landingViewModel.fetchUserData()
        setUpStandingsList()


        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT /*or ItemTouchHelper.DOWN or ItemTouchHelper.UP*/
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.adapterPosition
                standingsAdapter.removeItem(position)
                standingsAdapter.notifyDataSetChanged()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mActivityLandingBinding?.standingsRecyclerView)
        landingViewModel.standingsLiveData.observe(this, Observer { landingViewModel.updateStandingsList(it) })
    }
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModel(): LandingViewModel {
        landingViewModel = ViewModelProviders.of(this, viewModelFactory).get(LandingViewModel::class.java)
        return landingViewModel  as LandingViewModel  }

    override fun getLayoutId(): Int = R.layout.activity_landing
    override fun onItemClicked(obj: Any) {
        var user= obj as Users
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("URL",user.image )
        lastClickedUser=user
        startActivityForResult(intent, ASK_QUESTION_REQUEST);
    }
    private fun setUpStandingsList() {

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mActivityLandingBinding?.standingsRecyclerView?.itemAnimator = DefaultItemAnimator()
        mActivityLandingBinding?.standingsRecyclerView?.adapter = standingsAdapter
        standingsAdapter.listener=this
        Toast.makeText(this@LandingActivity, "Swipe to delete row ", Toast.LENGTH_SHORT).show()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // check if the request code is same as what is passed  here it is 1
        if (requestCode == ASK_QUESTION_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                if (data!=null){
                    val result = data?.getStringExtra(UserDetailsActivity.Result_DATA)

                    var position=landingViewModel.standingsArrayList.indexOf(lastClickedUser)
                    landingViewModel.standingsArrayList.removeAt(position)
                    standingsAdapter.removeItem(position)
                    standingsAdapter.notifyDataSetChanged()
                }
                // Use the data - in this case display it in a Toast.
              //  Toast.makeText(this, "Result: $result", Toast.LENGTH_LONG).show()
            }
        }
    }

}