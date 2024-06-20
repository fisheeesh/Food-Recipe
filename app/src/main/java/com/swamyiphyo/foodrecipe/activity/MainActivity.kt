package com.swamyiphyo.foodrecipe.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.swamyiphyo.foodrecipe.R
import com.swamyiphyo.foodrecipe.adapter.BaseAdapter
import com.swamyiphyo.foodrecipe.api.Presenter
import com.swamyiphyo.foodrecipe.api.RequestManager
import com.swamyiphyo.foodrecipe.databinding.ActivityMainBinding
import com.swamyiphyo.foodrecipe.databinding.LayoutRecipesBinding
import com.swamyiphyo.foodrecipe.databinding.RecipesBinding
import com.swamyiphyo.foodrecipe.model.Recipe

class MainActivity : AppCompatActivity(), Presenter {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var layoutRecipesBinding: LayoutRecipesBinding
    private lateinit var recipesBinding: RecipesBinding
    private lateinit var mainAdapter : BaseAdapter<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)

        RequestManager.getInstance().getRndRecipe(this, this)
    }
    override fun setUpUI(objList: ArrayList<Recipe>) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        BaseAdapter(R.layout.recipes, objList, false){
            _, data, view ->

            /**
             * with layout_recipes
             */
//            layoutRecipesBinding = LayoutRecipesBinding.bind(view)
//            layoutRecipesBinding.textViewDishName.text = data.title
//            layoutRecipesBinding.textViewDishName.isSelected = true
//            layoutRecipesBinding.person.text = "${data.servings} Servings"
//            layoutRecipesBinding.likes.text = "${data.aggregateLikes} Likes"
//            layoutRecipesBinding.duration.text = "${data.readyInMinutes} Minutes"
//            Picasso.get()
//                .load(data.image)
//                .into(layoutRecipesBinding.dishImage)

            /**
             * with recipes
             */
            recipesBinding = RecipesBinding.bind(view)
            recipesBinding.dishName.text = data.title
            recipesBinding.dishName.isSelected = true
            recipesBinding.person.text = "${data.servings} Servings"
            recipesBinding.like.text = "${data.aggregateLikes} Likes"
            recipesBinding.time.text = "${data.readyInMinutes} Minutes"
            Picasso.get()
                .load(data.image)
                .into(recipesBinding.imageView)

        }.also { mainAdapter = it }

        activityMainBinding.mainRV.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }
}