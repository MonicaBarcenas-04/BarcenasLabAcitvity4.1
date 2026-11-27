package com.example.barcenaslabacitvity41

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.main_recipes_list)
        titleEditText = findViewById(R.id.main_recipe_title)
        descriptionEditText = findViewById(R.id.main_recipe_description)
        val addSavoryButton = findViewById<Button>(R.id.main_add_savory_button)
        val addSweetButton = findViewById<Button>(R.id.main_add_sweet_button)

        recipeAdapter = RecipeAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recipeAdapter

        addSavoryButton.setOnClickListener {
            handleAddRecipe(Flavor.SAVORY)
        }

        addSweetButton.setOnClickListener {
            handleAddRecipe(Flavor.SWEET)
        }

        setupSwipeToDelete(recyclerView)
    }

    private fun handleAddRecipe(flavor: Flavor) {
        val title = titleEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()

        if (title.isEmpty()) {
            titleEditText.error = "Title is required"
            Toast.makeText(this, "Please enter a recipe title", Toast.LENGTH_SHORT).show()
            return
        }

        val recipe = Recipe(title = title, description = description, flavor = flavor)
        recipeAdapter.addRecipe(recipe)

        titleEditText.text.clear()
        descriptionEditText.text.clear()
        titleEditText.error = null
    }

    private fun setupSwipeToDelete(recyclerView: RecyclerView) {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
            ): Int {
                if (viewHolder is RecipeAdapter.FlavorViewHolder) {
                    return 0
                }
                return super.getSwipeDirs(recyclerView, viewHolder)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    recipeAdapter.removeAt(position)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
