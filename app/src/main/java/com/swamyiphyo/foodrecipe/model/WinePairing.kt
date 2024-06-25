package com.swamyiphyo.foodrecipe.model

data class WinePairing (
    var pairedWines: ArrayList<String>? = null,
    var pairingText: String? = null,
    var productMatches: ArrayList<ProductMatch>? = null
)