package com.swamyiphyo.foodrecipe

import com.swamyiphyo.foodrecipe.model.Recipe

interface Presenter {
    fun showProgress()
    fun hideProgress()
    fun setUpUI(objList : ArrayList<Recipe>)
}