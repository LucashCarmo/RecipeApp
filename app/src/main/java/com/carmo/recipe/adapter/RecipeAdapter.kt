package com.carmo.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.carmo.recipe.R
import com.carmo.recipe.models.Recipe
import kotlin.random.Random

class RecipeAdapter(private val context: Context, val listener: RecipeClickListener) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val recipeList = ArrayList<Recipe>()
    private val fullList = ArrayList<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.title.text = currentRecipe.title
        holder.title.isSelected = true
        holder.ingredientsTv.text = currentRecipe.ingredients
        holder.preparationTv.text = currentRecipe.preparation
        holder.date.text = currentRecipe.date
        holder.date.isSelected = true

        holder.recipesLayout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))

        holder.recipesLayout.setOnClickListener {
            listener.onItemClicked(recipeList[position])
        }

        holder.recipesLayout.setOnLongClickListener {
            listener.onLongItemClicked(recipeList[position], holder.recipesLayout)
            true // Indica que o evento foi consumido
        }
    }

    private fun randomColor(): Int {
        val list = listOf(
            R.color.NoteColor1,
            R.color.NoteColor2,
            R.color.NoteColor3,
            R.color.NoteColor4,
            R.color.NoteColor5,
            R.color.NoteColor6
        )
        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    fun updateList(newList: List<Recipe>) {
        fullList.clear()
        fullList.addAll(newList)

        recipeList.clear()
        recipeList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        recipeList.clear()

        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.ingredients?.lowercase()?.contains(search.lowercase()) == true
            ) {
                recipeList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipesLayout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val ingredientsTv = itemView.findViewById<TextView>(R.id.tv_ingredients)
        val preparationTv = itemView.findViewById<TextView>(R.id.tv_preparation)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }

    interface RecipeClickListener {
        fun onItemClicked(recipe: Recipe)
        fun onLongItemClicked(recipe: Recipe, cardView: CardView)
    }
}
