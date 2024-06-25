package com.swamyiphyo.foodrecipe.api

import com.swamyiphyo.foodrecipe.model.RecipeDetails
import com.swamyiphyo.foodrecipe.model.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * In this class, we will create methods for getting data from api
 * we need to add apiKey for any api call
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

    @GET("/recipes/{id}/information")
    fun getRecipeDetail(
        @Path("id") id : Int,
        @Query ("apiKey") apiKey : String,
    ) : Call<RecipeDetails>
}