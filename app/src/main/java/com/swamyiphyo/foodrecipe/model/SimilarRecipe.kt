package com.swamyiphyo.foodrecipe.model

data class SimilarRecipe(
    var id: Int = 0,
    var imageType: String? = null,
    var title: String? = null,
    var readyInMinutes: Int = 0,
    var servings: Int = 0,
    var sourceUrl: String? = null
)

