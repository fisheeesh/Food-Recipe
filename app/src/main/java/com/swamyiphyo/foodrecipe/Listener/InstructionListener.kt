package com.swamyiphyo.foodrecipe.Listener

import com.swamyiphyo.foodrecipe.databinding.LayoutInstructionListsBinding
import com.swamyiphyo.foodrecipe.databinding.LayoutStepsListBinding
import com.swamyiphyo.foodrecipe.model.Equipment
import com.swamyiphyo.foodrecipe.model.Ingredient
import com.swamyiphyo.foodrecipe.model.Instructions
import com.swamyiphyo.foodrecipe.model.Step

interface InstructionListener {
    fun showProgress()
    fun hideProgress()
    fun setUpRVForInstructions(objList: ArrayList<Instructions>)
    fun setUpRVForEquipment(objList: ArrayList<Equipment>, parent : LayoutStepsListBinding)
    fun setUpRVForIngredients(objList: ArrayList<Ingredient>, parent : LayoutStepsListBinding)
    fun setUpRVForSteps(objList : ArrayList<Step>, parent : LayoutInstructionListsBinding)
}