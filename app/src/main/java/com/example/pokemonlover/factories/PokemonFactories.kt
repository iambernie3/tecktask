package com.example.pokemonlover.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonlover.viewmodels.PokemonVM

@Suppress("UNCHECKED_CAST")
class PokemonFactories (): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PokemonVM::class.java)){
            return PokemonVM() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }
}