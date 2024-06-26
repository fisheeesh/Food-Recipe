package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.ExtendedIngredient
import com.swamyiphyo.foodrecipe.model.Ingredient
import com.swamyiphyo.foodrecipe.model.Recipe
import com.swamyiphyo.foodrecipe.model.RecipeDetails

interface RecipeDetailResponseListener {
    fun showProgress()
    fun hideProgress()
    fun setUpUI(obj : RecipeDetails)
    fun setUpRecyclerView(objList: List<ExtendedIngredient>)
}