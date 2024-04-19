package com.carmo.recipe.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.carmo.recipe.database.RecipeDatabase
import com.carmo.recipe.database.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val  repository : RecipeRepository

    val allnotes : LiveData<List<Recipe>>

    init {
        val dao = RecipeDatabase.getDatabase(application).gerRecipeDao()
        repository = RecipeRepository(dao)
        allnotes = repository.allRecipe
    }


    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {

        repository.delete(recipe)
    }

    fun insertRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {

        repository.insert(recipe)
    }


    fun updateRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(recipe)
    }

}