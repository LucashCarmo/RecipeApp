package com.carmo.recipe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carmo.recipe.databinding.ActivityAddRecipeBinding
import com.carmo.recipe.models.Recipe
import java.text.SimpleDateFormat
import java.util.Date


class AddRecipe : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding

    private lateinit var recipe : Recipe
    private lateinit var old_recipe : Recipe
    var isUpdate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            old_recipe = intent.getSerializableExtra("current_recipe") as Recipe
            binding.edTitle.setText(old_recipe.title)
            binding.edIngredients.setText(old_recipe.ingredients)
            binding.edPreparation.setText(old_recipe.preparation)
            isUpdate = true
        }catch (e : Exception){

            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.edTitle.text.toString()
            val ingredients = binding.edIngredients.text.toString()
            val preparation = binding.edPreparation.text.toString()

            if (title.isNotEmpty() && ingredients.isNotEmpty() && preparation.isNotEmpty()) {
                val formater = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
                val updatedRecipe = if (isUpdate) {
                    old_recipe.copy(
                        title = title,
                        ingredients = ingredients,
                        preparation = preparation,
                        date = formater.format(Date())
                    )
                } else {
                    Recipe(
                        null, // Um novo ID será atribuído pelo banco de dados
                        title,
                        ingredients,
                        preparation,
                        formater.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("recipe", updatedRecipe) // Coloca a receita como extra do Intent
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@AddRecipe, "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }

    }
}