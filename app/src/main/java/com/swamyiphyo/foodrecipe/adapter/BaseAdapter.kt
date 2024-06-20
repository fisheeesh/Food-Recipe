package com.swamyiphyo.foodrecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T>(
    private var layout : Int,
    private var items : List<T>,
    private var isRecyclable : Boolean,
    private val bindView : (position : Int, data : T, View) -> Unit
) : RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(position, items[position])
        holder.setIsRecyclable(isRecyclable)
    }
    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(position : Int, data : T) = bindView.invoke(position, data, itemView)
    }
}