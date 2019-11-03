package com.mvvm.ui.landing

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.util.Log
import com.mvvm.repository.DataManager
import com.mvvm.repository.models.mapped.Category
import com.mvvm.repository.models.mapped.Product
import com.mvvm.repository.models.mapped.SubCategory
import com.mvvm.ui.base.BaseViewModel
import com.mvvm.utils.rx.SchedulerProvider
import timber.log.Timber

class LandingViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider)
                      : BaseViewModel(dataManager,schedulerProvider),LifecycleObserver{
    val standingsLiveData: MutableLiveData<List<Product?>> by lazy { MutableLiveData<List<Product?>>() }
    val categoriesLiveData: MutableLiveData<List<Category?>> by lazy { MutableLiveData<List<Category?>>() }
    val subCategoriesLiveData: MutableLiveData<List<SubCategory?>> by lazy { MutableLiveData<List<SubCategory?>>() }
    val filterdProductLiveData: MutableLiveData<List<Product?>> by lazy { MutableLiveData<List<Product?>>() }
    val filterdCategoriesLiveData: MutableLiveData<List<Category?>> by lazy { MutableLiveData<List<Category?>>() }
    val filterdSubCategoriesLiveData: MutableLiveData<List<SubCategory?>> by lazy { MutableLiveData<List<SubCategory?>>() }
    val standingsArrayList: ObservableList<Product> = ObservableArrayList<Product>() as ObservableList<Product>
    var filtersArrayList=ArrayList<Product>()

    fun updateStandingsList(listOfAllMatches: List<Product?>?) {

        listOfAllMatches?.let {
            standingsArrayList.clear()
            standingsArrayList.addAll(it)
        }

    }

    fun getCategoriesData() {
        dataManager.getCategoriesData()?.subscribeOn(schedulerProvider.io())?.observeOn(schedulerProvider.ui())?.subscribe({
            standingsLiveData.value = it.productList
            filtersArrayList = it.productList
            categoriesLiveData.value = it.categoryList
            subCategoriesLiveData.value = it.subCategoryList
            filterdCategoriesLiveData.value = it.categoryList
            filterdSubCategoriesLiveData.value = it.subCategoryList
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

    fun filterData(idList: ArrayList<Int>?){
        val list: ObservableList<Product> = ObservableArrayList<Product>()
        filtersArrayList.forEach {
            idList?.forEach {id->
                if(it.productCategoryId==id){
                    list.add(it)
                }
            }

        }
        updateStandingsList(list)
    }

}