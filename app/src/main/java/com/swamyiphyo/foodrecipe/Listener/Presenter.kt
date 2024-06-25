package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.Recipe

interface Presenter {
    fun showProgress()
    fun hideProgress()
    fun setUpUI(objList : ArrayList<Recipe>)
}