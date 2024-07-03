package com.swamyiphyo.foodrecipe.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.swamyiphyo.foodrecipe.Listener.RndRecipeListener
import com.swamyiphyo.foodrecipe.Listener.RecipeDetailResponseListener
import com.swamyiphyo.foodrecipe.Listener.SimilarRecipesListener
import com.swamyiphyo.foodrecipe.R
import com.swamyiphyo.foodrecipe.model.ExtendedIngredient
import com.swamyiphyo.foodrecipe.model.Instructions
import com.swamyiphyo.foodrecipe.model.Recipe
import com.swamyiphyo.foodrecipe.model.RecipeDetails
import com.swamyiphyo.foodrecipe.model.RndRecipes
import com.swamyiphyo.foodrecipe.model.SimilarRecipe
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
    fun getRndRecipe(context : Context, obj : RndRecipeListener){
        val api = ApiClient.apiService.getRndRecipe(context.getString(R.string.api_key), "11")
        api.enqueue(object : Callback<RndRecipes> {
            override fun onResponse(p0: Call<RndRecipes>, p1: Response<RndRecipes>) {
                if(p1.isSuccessful){
                    val data = p1.body()
                    val objList = data?.recipes as ArrayList<Recipe>
                    obj.hideProgress()
                    obj.setUpUIForRndRecipe(objList)
                }
                else{
                    Log.d("TAG", "onResponse: ${p1.errorBody()}")
                    obj.showProgress()
                }
            }

            override fun onFailure(p0: Call<RndRecipes>, p1: Throwable) {
                Log.d("TAG", "onFailure: ${p1.message}")
                obj.showProgress()
            }

        })
    }
    fun getRecipeByTags(context : Context, obj : RndRecipeListener, tags : List<String>){
        val api = ApiClient.apiService.getRecipeByTags(context.getString(R.string.api_key),"9", tags)
        api.enqueue(object : Callback<RndRecipes>{
            override fun onResponse(p0: Call<RndRecipes>, p1: Response<RndRecipes>) {
                if(p1.isSuccessful){
                    val data = p1.body()
                    val objList = data?.recipes as ArrayList<Recipe>
                    obj.hideProgress()
                    obj.setUpUIForRndRecipe(objList)
                }
                else{
                    Log.d("TAG", "onResponse: ${p1.errorBody()}")
                    obj.showProgress()
                }
            }

            override fun onFailure(p0: Call<RndRecipes>, p1: Throwable) {
                Log.d("TAG", "onFailure: ${p1.message}")
                obj.showProgress()
            }

        })
    }
    fun getRecipeDetail(context: Context,resp : RecipeDetailResponseListener, id : Int){
        val api = ApiClient.apiService.getRecipeDetail(id, context.getString(R.string.api_key))
        api.enqueue(object  : Callback<RecipeDetails>{
            override fun onResponse(p0: Call<RecipeDetails>, p1: Response<RecipeDetails>) {
                if(p1.isSuccessful){
                    val response = p1.body()
                    resp.hideProgress()
                    resp.setUpUIForRecipeDetail(response!!)
                    resp.setUpRecyclerViewForIngredients(response.extendedIngredients as List<ExtendedIngredient>)
                }
                else{
                    resp.showProgress()
                    Log.d("TAG", "onResponse: ${p1.errorBody()}")
                }
            }

            override fun onFailure(p0: Call<RecipeDetails>, p1: Throwable) {
                resp.showProgress()
                Log.d("TAG", "onFailure: ${p1.message}")
            }

        })
    }
    fun getSimilarRecipes(context : Context, sim : SimilarRecipesListener, id : Int){
        val api = ApiClient.apiService.getSimilarRecipes(id, context.getString(R.string.api_key), "4")
        api.enqueue(object : Callback<List<SimilarRecipe>>{
            override fun onResponse(p0: Call<List<SimilarRecipe>>, p1: Response<List<SimilarRecipe>>) {
                if(p1.isSuccessful){
                    val response = p1.body()
                    val objList = response as ArrayList<SimilarRecipe>
                    if(objList.isEmpty()){
                        sim.showNoSimilar()
                    }
                    else{
                        sim.setUpUIForSimilarRecipes(objList)
                    }
                }
                else{
                    Log.d("Similar", "onResponse: ${p1.errorBody()}")
                }
            }

            override fun onFailure(p0: Call<List<SimilarRecipe>>, p1: Throwable) {
                Log.d("Similar Fail", "onFailure: ${p1.message}")
            }

        })
    }
}