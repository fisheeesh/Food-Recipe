package com.swamyiphyo.foodrecipe.model

data class ProductMatch(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var price: String? = null,
    var imageUrl: String? = null,
    var averageRating: Double = 0.0,
    var ratingCount: Double = 0.0,
    var score: Double = 0.0,
    var link: String? = null
)