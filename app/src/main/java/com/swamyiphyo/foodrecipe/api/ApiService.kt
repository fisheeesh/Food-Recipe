package com.swamyiphyo.foodrecipe.api

import com.swamyiphyo.foodrecipe.model.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * In this class, we will create methods for getting data from api
 */
interface ApiService {
    @GET("/recipes/random")
    fun getRndRecipe(
        @Query("apiKey") apiKey : String,
        @Query("number") number : String
    ) : Call<Root>

    @GET("/recipes/random")
    fun getRecipeByTags(
        @Query("apiKey") apiKey : String,
        @Query("number") number : String,
        @Query("include-tags") tags : List<String>
    ) : Call<Root>
}