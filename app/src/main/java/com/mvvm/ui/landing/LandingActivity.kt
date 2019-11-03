package com.mvvm.ui.landing

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.BindingAdapter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvvm.BR
import com.mvvm.R
import com.mvvm.databinding.ActivityLandingBinding
import com.mvvm.repository.models.mapped.Category
import com.mvvm.repository.models.mapped.Product
import com.mvvm.repository.models.mapped.SubCategory
import com.mvvm.ui.base.BaseActivity
import com.mvvm.ui.base.ItemClicked
import com.mvvm.ui.landing.adapter.LandingAdapter
import javax.inject.Inject


@BindingAdapter("bind:adapter")
fun observeStandingList(recyclerView: RecyclerView, standingList: List<Product>) {
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

    var categoryArrayList: ArrayList<Category>? = null
    var subCategory: ArrayList<SubCategory>? = null
    var isFirstTimeCategory = true
    var isFirstTimeSub = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityLandingBinding = getViewDataBinding()
        landingViewModel.getCategoriesData()
        setUpStandingsList()
        handleClicks()

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
        landingViewModel.categoriesLiveData.observe(this, Observer {
            categoryArrayList=it as ArrayList<Category>
            setCategorySpinner(it as ArrayList<Category>)
        })
        landingViewModel.subCategoriesLiveData.observe(this, Observer {
            subCategory=it as ArrayList<SubCategory>
            setSubCategorySpinner(it as ArrayList<SubCategory>,true)
        })
    }
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModel(): LandingViewModel {
        landingViewModel = ViewModelProviders.of(this, viewModelFactory).get(LandingViewModel::class.java)
        return landingViewModel  as LandingViewModel  }

    override fun getLayoutId(): Int = R.layout.activity_landing
    override fun onItemClicked(obj: Any) {

       // startActivityForResult(intent, ASK_QUESTION_REQUEST);
    }
    private fun setUpStandingsList() {

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mActivityLandingBinding?.standingsRecyclerView?.itemAnimator = DefaultItemAnimator()
        mActivityLandingBinding?.standingsRecyclerView?.adapter = standingsAdapter
        standingsAdapter.listener=this
        Toast.makeText(this@LandingActivity, "Swipe to delete row ", Toast.LENGTH_SHORT).show()

    }
    private fun setCategorySpinner(categoryArrayList: ArrayList<Category>) {

        val ls = Category()
        val text = "Select Category"

        ls.categoryName=text
        ls.categoryId=0
        categoryArrayList.add(0, ls)
        val dataAdapter = object : ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1,
            categoryArrayList) {
            override fun getCount(): Int {
                return categoryArrayList.size
            }

            override fun getDropDownView(position: Int, convertView: View?,
                                         parent: ViewGroup
            ): View {

                val inflater = getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view: View

                if (position == 0) {
                    view = inflater.inflate(R.layout.layout_hint_spinner, parent, false) // Hide first row
                } else {
                    view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
                    val label = view.findViewById<View>(android.R.id.text1) as TextView
                    label.text = categoryArrayList[position].categoryName
               //     label.typeface= Typeface.createFromAsset(this.context.assets,"fonts/NoirProRegular.ttf")
                    label.setTextColor(Color.parseColor("#093F89"))
                    label.textSize = 16f
                    //    label.typeface = Typeface.DEFAULT_BOLD
                    label.setPadding(20, 0, 0, 10)
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                val label = TextView(this.context)
                label.setTextColor(Color.GRAY)
            //    label.typeface= Typeface.createFromAsset(this.context.assets,"fonts/NoirProRegular.ttf")
                //  label.typeface = Typeface.DEFAULT_BOLD
                label.textSize = 16f

                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT)
                label.setPadding(20, 0, 0, 10)
                if (position == 0) {
                    label.text = ""
                    label.setText(categoryArrayList[position].categoryName) //"Hint to be displayed"
                } else {
                    label.setText(categoryArrayList[position].categoryName)
              //      label.typeface= Typeface.createFromAsset(this.context.assets,"fonts/NoirProRegular.ttf")
                    label.setTextColor(Color.parseColor("#093F89"))
                    //   label.typeface = Typeface.DEFAULT_BOLD
                }
                label.layoutParams = params
                return label
            }
        }
        mActivityLandingBinding?.categorySpinner?.setAdapter(dataAdapter)

    }

    private fun setSubCategorySpinner(subCategoryArrayList: ArrayList<SubCategory>,addFirst:Boolean) {

        if (addFirst){

            val ls = SubCategory()
            val text = "Select SubCategory"

            ls.subCategoryName=text
            ls.subCategoryId=0
            subCategoryArrayList.add(0, ls)
        }
        val dataAdapter = object : ArrayAdapter<SubCategory>(this, android.R.layout.simple_list_item_1,
            subCategoryArrayList) {
            override fun getCount(): Int {
                return subCategoryArrayList.size
            }

            override fun getDropDownView(position: Int, convertView: View?,
                                         parent: ViewGroup
            ): View {

                val inflater = getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view: View

                if (position == 0) {
                    view = inflater.inflate(R.layout.layout_hint_spinner, parent, false) // Hide first row
                } else {
                    view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
                    val label = view.findViewById<View>(android.R.id.text1) as TextView
                    label.text = subCategoryArrayList[position].subCategoryName
               //     label.typeface= Typeface.createFromAsset(this.context.assets,"fonts/NoirProRegular.ttf")
                    label.setTextColor(Color.parseColor("#093F89"))
                    label.textSize = 16f
                    //    label.typeface = Typeface.DEFAULT_BOLD
                    label.setPadding(20, 0, 0, 10)
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                val label = TextView(this.context)
                label.setTextColor(Color.GRAY)
            //    label.typeface= Typeface.createFromAsset(this.context.assets,"fonts/NoirProRegular.ttf")
                //  label.typeface = Typeface.DEFAULT_BOLD
                label.textSize = 16f

                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT)
                label.setPadding(20, 0, 0, 10)
                if (position == 0) {
                    label.text = ""
                    label.setText(subCategoryArrayList[position].subCategoryName) //"Hint to be displayed"
                } else {
                    label.setText(subCategoryArrayList[position].subCategoryName)
              //      label.typeface= Typeface.createFromAsset(this.context.assets,"fonts/NoirProRegular.ttf")
                    label.setTextColor(Color.parseColor("#093F89"))
                    //   label.typeface = Typeface.DEFAULT_BOLD
                }
                label.layoutParams = params
                return label
            }
        }
        mActivityLandingBinding?.subCategorySpinner?.setAdapter(dataAdapter)

    }
    private fun handleClicks(){
        mActivityLandingBinding?.categorySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
             //   subCategory?.let { setSubCategorySpinner(it) }
            if (!isFirstTimeCategory){
                subCategory?.let { setSubCategorySpinner(it,false) }
                isFirstTimeSub=true
                Handler().postDelayed({
                    landingViewModel.filterData(categoryArrayList?.get(position)?.childCategoryIdList)

                }, 300)
            }
                isFirstTimeCategory=false

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        mActivityLandingBinding?.subCategorySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (!isFirstTimeSub){

                var list= ArrayList<Int>()
                list.add(subCategory?.get(position)?.subCategoryId?:0)
                landingViewModel.filterData(list )
            }
                isFirstTimeSub=false
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}