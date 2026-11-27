package com.example.barcenaslabacitvity41

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_FLAVOR = 0
        const val VIEW_TYPE_RECIPE = 1
    }

    private val items: MutableList<ListItem> = mutableListOf(
        FlavorTitle(Flavor.SAVORY),
        FlavorTitle(Flavor.SWEET)
    )

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FlavorTitle -> VIEW_TYPE_FLAVOR
            is Recipe -> VIEW_TYPE_RECIPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_FLAVOR) {
            val view = inflater.inflate(R.layout.item_flavor_title, parent, false)
            FlavorViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_recipe_title, parent, false)
            RecipeViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is FlavorTitle -> (holder as FlavorViewHolder).bind(item)
            is Recipe -> (holder as RecipeViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun addRecipe(recipe: Recipe) {
        val insertIndex = if (recipe.flavor == Flavor.SAVORY) {
            val sweetIndex = items.indexOfFirst { (it is FlavorTitle) && (it.flavor == Flavor.SWEET) }
            if (sweetIndex != -1) sweetIndex else items.size
        } else {
            items.size
        }
        items.add(insertIndex, recipe)
        notifyItemInserted(insertIndex)
    }

    fun removeAt(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class FlavorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flavorTitleText: TextView = itemView.findViewById(R.id.text_flavor_title)

        fun bind(flavorTitle: FlavorTitle) {
            flavorTitleText.text = flavorTitle.flavor.displayLabel
        }
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeTitleText: TextView = itemView.findViewById(R.id.text_recipe_title)

        fun bind(recipe: Recipe) {
            recipeTitleText.text = recipe.title
            itemView.setOnClickListener {
                AlertDialog.Builder(itemView.context)
                    .setTitle(recipe.title)
                    .setMessage(recipe.description)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
}
