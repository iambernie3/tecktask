package com.example.pokemonlover.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pokemonlover.apputil.NativePref
import com.example.pokemonlover.interfaces.IPokemonResponse
import com.example.pokemonlover.volley.VolleyRequest

class PokemonVM(): ViewModel() {

    fun fetchPokemon(volleyRequest: VolleyRequest,listener: IPokemonResponse){
        val url = NativePref().getNative1()
        volleyRequest.getRequest(url,object:IPokemonResponse {
            override fun pokemonResponse(statusCode: String, data: String) {
                listener.pokemonResponse(statusCode,data)
            }
        })
    }

    fun fetchSprites(url:String,volleyRequest: VolleyRequest,listener: IPokemonResponse) {
        volleyRequest.getRequest(url,object:IPokemonResponse {
            override fun pokemonResponse(statusCode: String, data: String) {
                listener.pokemonResponse(statusCode,data)
            }
        })
    }

    fun fetchEffects(url:String,volleyRequest: VolleyRequest,listener: IPokemonResponse) {
        volleyRequest.getRequest(url,object:IPokemonResponse {
            override fun pokemonResponse(statusCode: String, data: String) {
                listener.pokemonResponse(statusCode,data)
            }
        })
    }
}