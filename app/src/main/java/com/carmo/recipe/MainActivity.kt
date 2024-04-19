package com.carmo.recipe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.carmo.recipe.adapter.RecipeAdapter
import com.carmo.recipe.database.RecipeDatabase
import com.carmo.recipe.databinding.ActivityMainBinding
import com.carmo.recipe.models.Recipe
import com.carmo.recipe.models.RecipeViewModel

class MainActivity : AppCompatActivity(), RecipeAdapter.RecipeClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: RecipeDatabase
    lateinit var viewModel: RecipeViewModel
    lateinit var adapter: RecipeAdapter
    lateinit var selectedRecipe: Recipe

    private val updateRecipe = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val recipe = result.data?.getSerializableExtra("recipe") as? Recipe // Obtém a receita do Intent
            if (recipe != null) {
                // Faça o que for necessário com a receita atualizada
                viewModel.updateRecipe(recipe)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(RecipeViewModel::class.java)

        viewModel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

        database = RecipeDatabase.getDatabase(this)

    }

    private fun initUI(){

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = RecipeAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            if (result.resultCode == Activity.RESULT_OK){

                val recipe = result.data?.getSerializableExtra("recipe") as? Recipe
                if (recipe != null){
                    viewModel.insertRecipe(recipe)
                }
            }
        }

        binding.fbAddNote.setOnClickListener {

            val intent = Intent(this, AddRecipe::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null){
                    adapter.filterList(newText)
                }
                return true
            }

        })

    }

    override fun onItemClicked(recipe: Recipe) {
        val intent = Intent(this@MainActivity, AddRecipe::class.java)
        intent.putExtra("current_recipe", recipe) // Passa a receita atual para a Activity AddRecipe
        updateRecipe.launch(intent)
    }

    override fun onLongItemClicked(recipe: Recipe, cardView: CardView) {
       selectedRecipe = recipe
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu( this, cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note){
            viewModel.deleteRecipe(selectedRecipe)
            return true
        }
        return false
    }
}