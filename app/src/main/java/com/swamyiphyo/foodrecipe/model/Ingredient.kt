package com.swamyiphyo.foodrecipe.model

data class Ingredient(
    var id: Int = 0,
    var name: String? = null,
    var localizedName: String? = null,
    var image: String? = null
)