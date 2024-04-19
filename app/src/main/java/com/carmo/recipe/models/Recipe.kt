package com.carmo.recipe.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipes_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "ingredients") val ingredients : String?,
    @ColumnInfo(name = "preparation") val preparation : String?,
    @ColumnInfo(name = "date") val date : String?

) : Serializable
