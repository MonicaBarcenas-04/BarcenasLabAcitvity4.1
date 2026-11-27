package com.example.barcenaslabacitvity41

enum class Flavor(val displayLabel: String) {
    SAVORY("Savory"),
    SWEET("Sweet")
}

sealed class ListItem

data class FlavorTitle(val flavor: Flavor) : ListItem()

data class Recipe(
    val title: String,
    val description: String,
    val flavor: Flavor,
) : ListItem()
