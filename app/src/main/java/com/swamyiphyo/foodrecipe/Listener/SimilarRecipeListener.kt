package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.SimilarRecipe

interface SimilarRecipeListener {
    fun setSimilarRV(objList : ArrayList<SimilarRecipe>)
    fun showProgress()
    fun hideProgress()

}