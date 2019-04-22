package com.nerdery.slicesapp

import androidx.annotation.DrawableRes

data class Pokemon(val id: Int,
              val name: String,
              @DrawableRes val image: Int,
              val types: List<PokemonType>,
              val hp: Int,
              val attack: Int,
              val defense: Int,
              val url: String) {
    companion object {
        fun createPokemonList(): List<Pokemon> {
            val pokemonList = mutableListOf<Pokemon>()
            pokemonList.add(Pokemon(1, "Bulbasor", R.drawable.bulbasaur, mutableListOf(PokemonType.GRASS), 30, 30, 30, "https://bulbapedia.bulbagarden.net/wiki/Bulbasaur_(Pok%C3%A9mon)"))
            pokemonList.add(Pokemon(2, "Ivysoar", R.drawable.ivysaur, mutableListOf(PokemonType.GRASS), 70, 70, 70, "https://bulbapedia.bulbagarden.net/wiki/Ivysaur_(Pok%C3%A9mon)"))
            pokemonList.add(Pokemon(3, "Venosaur", R.drawable.venusaur, mutableListOf(PokemonType.GRASS), 100, 100, 100, "https://bulbapedia.bulbagarden.net/wiki/Venusaur_(Pok%C3%A9mon)"))
            pokemonList.add(Pokemon(4, "Charmander", R.drawable.charmander, mutableListOf(PokemonType.FIRE), 30, 30, 30, "https://bulbapedia.bulbagarden.net/wiki/Charmander_(Pok%C3%A9mon)"))
            pokemonList.add(Pokemon(5, "Charmeleon", R.drawable.charmeleon, mutableListOf(PokemonType.FIRE), 70, 70, 70, "https://bulbapedia.bulbagarden.net/wiki/Charmeleon_(Pok%C3%A9mon)"))
            pokemonList.add(Pokemon(6, "Charizard", R.drawable.charizard, mutableListOf(PokemonType.FIRE), 100, 100, 100, "https://bulbapedia.bulbagarden.net/wiki/Charizard_(Pok%C3%A9mon)"))
            pokemonList.add(Pokemon(7, "Squirtle", R.drawable.squirtle, mutableListOf(PokemonType.WATER), 30, 30, 30, "https://bulbapedia.bulbagarden.net/wiki/Squirtle_(Pok%C3%A9mon)"))
            pokemonList.add(Pokemon(8, "Warturtle", R.drawable.wartortle, mutableListOf(PokemonType.WATER), 70, 70, 70, "https://bulbapedia.bulbagarden.net/wiki/Wartortle_(Pok%C3%A9mon)"))
            pokemonList.add(Pokemon(9, "Blastoise", R.drawable.blastoise, mutableListOf(PokemonType.WATER), 100, 100, 100, "https://bulbapedia.bulbagarden.net/wiki/Blastoise_(Pok%C3%A9mon)"))

            return pokemonList
        }
    }
}

enum class PokemonType {
    GRASS, FIRE, WATER, ICE, ELECTRIC, GROUND, POISON, FLYING, DRAGON, BUG, DARK, GHOST, PSYCIC,
}