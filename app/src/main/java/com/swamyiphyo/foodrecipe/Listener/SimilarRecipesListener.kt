package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.SimilarRecipe

interface SimilarRecipesListener {
    fun setUpUIForSimilarRecipes(objList: ArrayList<SimilarRecipe>)
    fun showNoSimilar()
}