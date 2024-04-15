package com.carmo.recipe.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "ingredients") val ingredients : String?,
    @ColumnInfo(name = "preparation") val preparation : String?,
    @ColumnInfo(name = "date") val date : String?

)
