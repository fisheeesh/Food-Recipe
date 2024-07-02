package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.ExtendedIngredient
import com.swamyiphyo.foodrecipe.model.Ingredient
import com.swamyiphyo.foodrecipe.model.Recipe
import com.swamyiphyo.foodrecipe.model.RecipeDetails

interface RecipeDetailResponseListener {
    fun showProgress()
    fun hideProgress()
    fun setUpUIForRecipeDetail(obj : RecipeDetails)
    fun setUpRecyclerViewForIngredients(objList: List<ExtendedIngredient>)
}