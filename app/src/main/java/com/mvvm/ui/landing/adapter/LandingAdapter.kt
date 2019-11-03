package com.mvvm.ui.landing.adapter

import com.mvvm.BR
import com.mvvm.R
import com.mvvm.repository.models.mapped.Product
import com.mvvm.ui.base.ItemClicked
import com.mvvm.ui.base.RecyclerBaseAdapter
import com.mvvm.ui.base.RecyclerViewHolder

class LandingAdapter (private val listOfStandings: MutableList<Product>) : RecyclerBaseAdapter<Product>(),
    ItemClicked {
    var listener :ItemClicked?=null
    override fun onItemClicked(obj: Any) {
        listener?.onItemClicked(obj)
    }

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_product

    override fun getViewModel(position: Int): Any? {
       return listOfStandings[position]
    }

    override fun clearAllItems() {
        listOfStandings.clear()
    }

    override fun addAllItems(items: List<Product>) {
        listOfStandings.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
    getViewModel(position)?.let {
        //(it as Product).listener = this
        val bindingStatus= holder.binding.setVariable(BR.viewModel,it)
        if (!bindingStatus)
            throw IllegalStateException("Binding ${holder.binding} viewModel variable name should be 'viewModel'")
        holder.binding.executePendingBindings()
    }
    }

    fun removeItem(position: Int){
        listOfStandings.removeAt(position)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = listOfStandings.size

}