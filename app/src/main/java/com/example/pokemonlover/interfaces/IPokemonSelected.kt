package com.example.pokemonlover.interfaces

import com.example.pokemonlover.models.PokemonModel

interface IPokemonSelected {
    fun onSelectedPokemon(model:PokemonModel)
}