package com.example.pokemonlover.models

import com.google.gson.annotations.SerializedName

class SpritesModel (
    @SerializedName(value = "back_default")
    val backDefault:String,

    @SerializedName(value = "back_shiny")
    val backShiny:String
        )