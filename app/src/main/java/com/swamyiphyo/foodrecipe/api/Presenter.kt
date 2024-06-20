package com.swamyiphyo.foodrecipe.api

import com.swamyiphyo.foodrecipe.model.Recipe

interface Presenter {
    fun setUpUI(objList : ArrayList<Recipe>)
}