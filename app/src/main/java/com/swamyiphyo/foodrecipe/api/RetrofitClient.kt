package com.swamyiphyo.foodrecipe.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private const val BASE_URL = "https://api.spoonacular.com/"

    val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

/**
 * This class will help Request Manager for calling methods from ApiService with its instance
 * which is created by using Retrofit
 */
object ApiClient{
    val apiService : ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}