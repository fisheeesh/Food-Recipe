package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.SimilarRecipe

interface SimilarRecipesListener {
    fun showProgress()
    fun hideProgress()
    fun setUpUIForSimilarRecipes(objList: ArrayList<SimilarRecipe>)
    fun showNoSimilar()
}