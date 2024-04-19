package com.carmo.recipe.database

import androidx.lifecycle.LiveData
import com.carmo.recipe.models.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipe : LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insert(recipe: Recipe){
        recipeDao.insert(recipe)
    }

    suspend fun delete(recipe: Recipe){
        recipeDao.delete(recipe)
    }

    suspend fun update(recipe: Recipe){
        recipeDao.update(recipe.id, recipe.title, recipe.ingredients, recipe.preparation)
    }


}