package com.carmo.recipe.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carmo.recipe.Models.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe : Recipe)

    @Delete
    suspend fun delete(recipe : Recipe)

    fun getAllRecipes() : LiveData<List<Recipe>>

    @Query("UPDATE recipes_table Set title = :title, ingredients = :ingredients, preparation = :preparation WHERE id = :id")
    suspend fun update(id : Int?, title : String?, ingredients : String?, preparation : String?)
}