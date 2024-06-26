package com.swamyiphyo.foodrecipe.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.swamyiphyo.foodrecipe.R
import com.swamyiphyo.foodrecipe.Utils.gone
import com.swamyiphyo.foodrecipe.Utils.visible
import com.swamyiphyo.foodrecipe.adapter.BaseAdapter
import com.swamyiphyo.foodrecipe.Listener.Presenter
import com.swamyiphyo.foodrecipe.Listener.RecipeDetailResponseListener
import com.swamyiphyo.foodrecipe.api.RequestManager
import com.swamyiphyo.foodrecipe.databinding.ActivityMainBinding
import com.swamyiphyo.foodrecipe.databinding.LayoutRecipesBinding
import com.swamyiphyo.foodrecipe.model.Recipe

class MainActivity : AppCompatActivity(), Presenter, SearchView.OnQueryTextListener{
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var layoutRecipesBinding: LayoutRecipesBinding
    private lateinit var mainAdapter : BaseAdapter<Recipe>
    private var tags = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)

        spinnerListener()

        /**
         * dun forget to user searchView from androidx provide a consistent
         * and reliable search experience for your users across a wide range of Android devices
         */
        activityMainBinding.searchHome.setOnQueryTextListener(this)
    }

    override fun showProgress() = activityMainBinding.loading.visible()

    override fun hideProgress() = activityMainBinding.loading.gone()

    @SuppressLint("SetTextI18n")
    override fun setUpUI(objList: ArrayList<Recipe>) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        BaseAdapter(R.layout.layout_recipes, objList, false){
            _, data, view ->

            /**
             * with layout_recipes
             */
            layoutRecipesBinding = LayoutRecipesBinding.bind(view)
            layoutRecipesBinding.textViewDishName.text = data.title
//            we need to control it to make it marquee with isSelected = true
            layoutRecipesBinding.textViewDishName.isSelected = true
            layoutRecipesBinding.person.text = "${data.servings} Servings"
            layoutRecipesBinding.likes.text = "${data.aggregateLikes} Likes"
            layoutRecipesBinding.duration.text = "${data.readyInMinutes} Minutes"
            Picasso.get()
                .load(data.image)
                .into(layoutRecipesBinding.dishImage)

            /**
             * when user click recipe card, use will be navigated to
             * the specific recipe page according to recipeId
             */
            layoutRecipesBinding.rndRecipes.setOnClickListener(){
                val recipeId = data.id.toString()
                startActivity(Intent(this@MainActivity, RecipeDetailActivity::class.java)
                    .putExtra("id", recipeId))
            }

            val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
            view.animation = animation

        }.also { mainAdapter = it }

        activityMainBinding.mainRV.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private fun spinnerListener(){
        val spinnerListener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                tags.clear()
                /**
                 * get selected item from the spinner based on its position
                 * we provide String as data to our spinner adapter so the result should be String
                 * That's why we need to cast with toString()
                 */
                val selectedTag = p0?.getItemAtPosition(p2).toString()
                tags.add(selectedTag)
                RequestManager.getInstance().getRecipeByTags(this@MainActivity, this@MainActivity, tags)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        /**
         * We want to display data on the spinner so we need to use adapter
         * which will be act as a bridge between spinner and data we want to display in it
         */
        val arrayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.tags,
            R.layout.spinner_texts
        )
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text)
        activityMainBinding.spinnerTags.adapter = arrayAdapter

        /**
         * we need set onItemSelectedListener to our spinner to activate the spinnerListener
         */
        activityMainBinding.spinnerTags.onItemSelectedListener = spinnerListener
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        tags.clear()
        tags.add(query!!)
        RequestManager.getInstance().getRecipeByTags(this, this, tags)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
       return false
    }
}