package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.model.Recipe

interface RndRecipeListener {
    fun showProgress()
    fun hideProgress()
    fun setUpUIForRndRecipe(objList : ArrayList<Recipe>)
}