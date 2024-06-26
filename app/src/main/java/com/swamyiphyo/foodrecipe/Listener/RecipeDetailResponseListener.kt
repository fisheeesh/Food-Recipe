package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.Recipe
import com.swamyiphyo.foodrecipe.model.RecipeDetails

interface RecipeDetailResponseListener {
    fun showProgress()
    fun hideProgress()
    fun setUpUI(obj : RecipeDetails)
}