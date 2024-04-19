package com.carmo.recipe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carmo.recipe.models.Recipe
import com.carmo.recipe.utilities.DATABASE_NAME

@Database(entities = arrayOf(Recipe::class), version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun gerRecipeDao() :RecipeDao

    companion object{

        @Volatile
        private var INSTANCE : RecipeDatabase? = null

        fun getDatabase(context: Context) : RecipeDatabase{

            return INSTANCE ?: synchronized(this){

                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instant

                instant
            }
        }
    }
}