package com.swamyiphyo.foodrecipe.activity

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.swamyiphyo.foodrecipe.Listener.RecipeDetailResponseListener
import com.swamyiphyo.foodrecipe.R
import com.swamyiphyo.foodrecipe.Utils.gone
import com.swamyiphyo.foodrecipe.Utils.visible
import com.swamyiphyo.foodrecipe.adapter.BaseAdapter
import com.swamyiphyo.foodrecipe.api.RequestManager
import com.swamyiphyo.foodrecipe.databinding.ActivityRecipeDetailBinding
import com.swamyiphyo.foodrecipe.databinding.MealsIngredientsBinding
import com.swamyiphyo.foodrecipe.model.ExtendedIngredient
import com.swamyiphyo.foodrecipe.model.RecipeDetails

class RecipeDetailActivity : AppCompatActivity(), RecipeDetailResponseListener {
    private lateinit var binding: ActivityRecipeDetailBinding
    private lateinit var ingredientsBinding : MealsIngredientsBinding
    private lateinit var mainAdapter : BaseAdapter<ExtendedIngredient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = ActivityRecipeDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val value = intent.getStringExtra("id")
        val recipeId = value?.toInt()

        RequestManager.getInstance().getRecipeDetail(this,this, recipeId!!)
    }

    override fun showProgress() = binding.loading.visible()

    override fun hideProgress() = binding.loading.gone()

    override fun setUpUI(obj: RecipeDetails) {
        binding.recipeName.text = obj.title
        binding.recipeSource.text = obj.sourceName
        binding.summary.text = obj.summary
        Picasso.get().load(obj.image).into(binding.recipeImage)
    }

    override fun setUpRecyclerView(objList: List<ExtendedIngredient>) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        BaseAdapter(R.layout.meals_ingredients, objList, false){
            _, data, view ->

            ingredientsBinding = MealsIngredientsBinding.bind(view)

            ingredientsBinding.ingreName.text = data.name
            ingredientsBinding.ingreName.isSelected = true
            ingredientsBinding.ingreQuality.text = data.original
            ingredientsBinding.ingreQuality.isSelected = true
            Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + data.image).into(ingredientsBinding.ingreImage)

            val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
            view.animation = animation

        }.also { mainAdapter = it }

        binding.RVIngredients.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }
}