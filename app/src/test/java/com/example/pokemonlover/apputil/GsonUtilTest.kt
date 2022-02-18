package com.example.pokemonlover.apputil

import com.example.pokemonlover.models.PokemonModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GsonUtilTest {

    /***
     * ...test with JSONArray data
     * ... return not empty
     */

    @Test
    fun jsonToArrayListTest(){
        val json = "[{\"name\":\"bulbasaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/1/\"},{\"name\":\"ivysaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/2/\"}]"
        val result = GsonUtil.jsonToArrayList(json,PokemonModel::class.java)
        assertThat(result).isNotEmpty()
    }

    /***
     * ...test with JsonObject
     * ... return exception
     */

    @Test
    fun jsonToArrayListJsonObject(){
        val json = "{\"name\":\"bulbasaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/1/\"},{\"name\":\"ivysaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/2/\"}"
        val result = GsonUtil.jsonToArrayList(json,PokemonModel::class.java)
        assertThat(result).isNull()
    }

    /***
     * ...test with empty data
     * ... return null
     */
    @Test
    fun jsonToArrayListTestEmpty(){
        val json = ""
        val result = GsonUtil.jsonToArrayList(json,PokemonModel::class.java)
        assertThat(result).isNull()
    }

}