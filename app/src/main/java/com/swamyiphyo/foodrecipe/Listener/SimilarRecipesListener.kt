package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.SimilarRecipe

interface SimilarRecipesListener {
    fun setUpUIForSimilarRecipes(objList: List<SimilarRecipe>)
}