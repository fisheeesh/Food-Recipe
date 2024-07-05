package com.swamyiphyo.foodrecipe.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.swamyiphyo.foodrecipe.Listener.InstructionListener
import com.swamyiphyo.foodrecipe.Listener.RecipeDetailResponseListener
import com.swamyiphyo.foodrecipe.Listener.SimilarRecipesListener
import com.swamyiphyo.foodrecipe.R
import com.swamyiphyo.foodrecipe.Utils.gone
import com.swamyiphyo.foodrecipe.Utils.visible
import com.swamyiphyo.foodrecipe.adapter.BaseAdapter
import com.swamyiphyo.foodrecipe.api.RequestManager
import com.swamyiphyo.foodrecipe.databinding.ActivityRecipeDetailBinding
import com.swamyiphyo.foodrecipe.databinding.LayoutInstructionListsBinding
import com.swamyiphyo.foodrecipe.databinding.LayoutMealSimilarBinding
import com.swamyiphyo.foodrecipe.databinding.LayoutStepItemsListBinding
import com.swamyiphyo.foodrecipe.databinding.LayoutStepsListBinding
import com.swamyiphyo.foodrecipe.model.Equipment
import com.swamyiphyo.foodrecipe.model.Ingredient
import com.swamyiphyo.foodrecipe.model.Instructions
import com.swamyiphyo.foodrecipe.model.RecipeDetails
import com.swamyiphyo.foodrecipe.model.SimilarRecipe
import com.swamyiphyo.foodrecipe.model.Step

class RecipeDetailActivity : AppCompatActivity(), RecipeDetailResponseListener, SimilarRecipesListener, InstructionListener{
    //main
    private lateinit var binding: ActivityRecipeDetailBinding
    //similar
    private lateinit var similarBinding : LayoutMealSimilarBinding
    private lateinit var similarAdapter : BaseAdapter<SimilarRecipe>
    //instruction
    private lateinit var instructionBinding : LayoutInstructionListsBinding
    private lateinit var instructionAdapter : BaseAdapter<Instructions>
    //steps
    private lateinit var stepBinding : LayoutStepsListBinding
    private lateinit var stepAdapter : BaseAdapter<Step>
    //equipment
    private lateinit var itemsBinding : LayoutStepItemsListBinding
    private lateinit var equipmentAdapter : BaseAdapter<Equipment>
    //ingredient
    private lateinit var ingredientAdapter : BaseAdapter<Ingredient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val value = intent.getStringExtra("id")
        val recipeId = value?.toInt()

        if (recipeId != null) {
            RequestManager.getInstance().getRecipeDetail(this, this, recipeId)
            RequestManager.getInstance().getSimilarRecipes(this, this, recipeId)
            RequestManager.getInstance().getRecipeInstruction(this, this, recipeId)
        }
    }

    override fun showProgress() = binding.loading.visible()

    override fun hideProgress() = binding.loading.gone()


    override fun setUpUIForRecipeDetail(obj: RecipeDetails) {
        binding.textViewRecipeName.text = obj.title
        binding.textViewRecipeSource.text = obj.sourceName
        binding.textViewMealSummary.text = obj.summary
        Picasso.get().load(obj.image).into(binding.imageViewRecipeImage)
    }

    override fun setUpUIForSimilarRecipes(objList: ArrayList<SimilarRecipe>) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        BaseAdapter(R.layout.layout_meal_similar, objList, false){
            _, data, view ->

            similarBinding = LayoutMealSimilarBinding.bind(view)

            similarBinding.textViewSimilarTitle.text = data.title
            similarBinding.textViewSimilarTitle.isSelected = true
            similarBinding.textViewSimilarServing.text = "${data.servings} Persons"
            similarBinding.textViewSimilarServing.isSelected = true
            Picasso.get().load("https://img.spoonacular.com/recipes/${data.id}-556x370.${data.imageType}").into(similarBinding.imageViewSimilarImage)

            val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
            view.animation = animation

            similarBinding.mealSimilarHolder.setOnClickListener(){
                val recipeId = data.id.toString()
                startActivity(
                    Intent(this@RecipeDetailActivity, RecipeDetailActivity::class.java)
                    .putExtra("id", recipeId))
            }
        }.also { similarAdapter = it }

        binding.mealSimilarRecipes.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = similarAdapter
        }
    }

    override fun showNoSimilar() {
        binding.textViewSimilarRecipes.apply {
            text = getString(R.string.no_similar)
            setPadding(8,8,8,8)
            textSize = 22F
        }

    }

    override fun setUpRVForInstructions(objList: ArrayList<Instructions>) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        BaseAdapter(R.layout.layout_instruction_lists, objList, false){
            _, data, view ->

            instructionBinding = LayoutInstructionListsBinding.bind(view)

            setUpRVForSteps(data.steps as ArrayList<Step>, instructionBinding)

        }.also { instructionAdapter = it }

        binding.RVMealInstructions.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = instructionAdapter
        }
    }

    override fun setUpRVForEquipment(objList: ArrayList<Equipment>, parent : LayoutStepsListBinding) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        BaseAdapter(R.layout.layout_step_items_list, objList, false){
            _, data, view ->

            itemsBinding = LayoutStepItemsListBinding.bind(view)

            itemsBinding.textViewInstructionsStepName.text = data.name
            itemsBinding.textViewInstructionsStepName.isSelected = true
            Picasso.get().load("https://spoonacular.com/cdn/equipment_100x100/" + data.image).into(itemsBinding.imageViewInstructionsStepItems)
        }.also { equipmentAdapter = it }
        parent.RVInstructionsEquipments.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = equipmentAdapter
        }
    }

    override fun setUpRVForIngredients(objList: ArrayList<Ingredient>, parent : LayoutStepsListBinding) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        BaseAdapter(R.layout.layout_step_items_list, objList, false){
            _, data, view ->

            itemsBinding = LayoutStepItemsListBinding.bind(view)

            itemsBinding.textViewInstructionsStepName.text = data.name
            itemsBinding.textViewInstructionsStepName.isSelected = true
            Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/${data.image}").into(itemsBinding.imageViewInstructionsStepItems)
        }.also { ingredientAdapter = it }

        parent.RVInstructionsIngredients.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = ingredientAdapter
        }
    }

    override fun setUpRVForSteps(objList: ArrayList<Step>, parent: LayoutInstructionListsBinding) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        BaseAdapter(R.layout.layout_steps_list, objList, false){
            _, data, view ->

            stepBinding = LayoutStepsListBinding.bind(view)

            stepBinding.textViewInstructionsStepTitle.text = data.step
            stepBinding.textViewInstructionsStepNumber.text = data.number.toString()

            setUpRVForEquipment(data.equipment as ArrayList<Equipment>, stepBinding)
            setUpRVForIngredients(data.ingredients as ArrayList<Ingredient>, stepBinding)

        }.also { stepAdapter = it }

        parent.RVInstructionsSteps.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = stepAdapter
        }
    }
}
