package com.swamyiphyo.foodrecipe.model

data class Step (
    var number: Int = 0,
    var step: String? = null,
    var ingredients: ArrayList<Ingredient>? = null,
    var equipment: ArrayList<Equipment>? = null,
    var length: Length? = null
)