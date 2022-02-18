package com.example.pokemonlover.apputil

import com.example.pokemonlover.models.AbilitiesModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StringUtilTest{

    /**
     *  ...test with valid parameters
     */
    @Test
    fun getStringAbilitiesTest(){
        val list = ArrayList<AbilitiesModel>()
        list.add(AbilitiesModel("overgrow","https://pokeapi.co/api/v2/ability/65/"))
        val result = StringUtil.getStringAbilities(list)
        assertThat(result).isNotEmpty()
    }

    /**
     *  ...test with empty list
     */
    @Test
    fun getStringAbilitiesTest2(){
        val list = ArrayList<AbilitiesModel>()
        val result = StringUtil.getStringAbilities(list)
        assertThat(result).isEmpty()
    }
}