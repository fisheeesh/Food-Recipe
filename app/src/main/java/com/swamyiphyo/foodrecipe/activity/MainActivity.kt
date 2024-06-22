package com.swamyiphyo.foodrecipe.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.swamyiphyo.foodrecipe.R
import com.swamyiphyo.foodrecipe.Utils.gone
import com.swamyiphyo.foodrecipe.Utils.visible
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
    private lateinit var mainAdapter : BaseAdapter<Recipe>
    private var tags = ArrayList<String>()

    private val spinnerListener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)

//        RequestManager.getInstance().getRndRecipe(this, this)

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

        activityMainBinding.spinnerTags.onItemSelectedListener = spinnerListener
    }

    override fun showProgress() {
        activityMainBinding.loading.visible()
    }

    override fun hideProgress() {
        activityMainBinding.loading.gone()
    }

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

        }.also { mainAdapter = it }

        activityMainBinding.mainRV.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }
}