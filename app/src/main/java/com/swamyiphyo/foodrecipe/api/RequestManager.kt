package com.swamyiphyo.foodrecipe.api

import android.content.Context
import android.util.Log
import com.swamyiphyo.foodrecipe.R
import com.swamyiphyo.foodrecipe.model.Recipe
import com.swamyiphyo.foodrecipe.model.Root
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This class will call ApiService methods by using apiClient instance to get data from api server
 * which we normally done in UI file
 */
class RequestManager private constructor(){
    /**
     * Like design pattern singleton, dun want to panic with reqManager Obj
     * Only one instance of this class will be used through this application
     * That's why its has private constructor to prevent it
     */
    companion object{
        private val requestManager = RequestManager()
        fun getInstance() : RequestManager{
            return requestManager
        }
    }
    fun getRndRecipe(context : Context, obj : Presenter){
        val api = ApiClient.apiService.getRndRecipe(context.getString(R.string.api_key), "11")
        api.enqueue(object : Callback<Root> {
            override fun onResponse(p0: Call<Root>, p1: Response<Root>) {
                if(p1.isSuccessful){
                    val data = p1.body()
                    val objList = data?.recipes as ArrayList<Recipe>
                    obj.setUpUI(objList)
                }
                else{
                    Log.d("TAG", "onResponse: ${p1.errorBody()}")
                }
            }

            override fun onFailure(p0: Call<Root>, p1: Throwable) {
                Log.d("TAG", "onFailure: ${p1.message}")
            }

        })
    }
}