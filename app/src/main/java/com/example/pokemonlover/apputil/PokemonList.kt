package com.example.pokemonlover.apputil

import android.util.Log
import com.example.pokemonlover.activities.MainActivity
import com.example.pokemonlover.models.PokemonModel
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class PokemonList {
    fun getPokemonList(data:String):List<PokemonModel> {
        val list:ArrayList<PokemonModel> = ArrayList()

        try {
            val gson = Gson()
            val jsonData = JSONObject(data)
            val jsonArray = jsonData.getJSONArray("results")
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val model = gson.fromJson(jsonObject.toString(),PokemonModel::class.java)
                list.add(model)
            }
        }catch (e: JSONException){
            e.printStackTrace()
            Log.e("PokemonList","exception: ${e.message}")
        }

        return list
    }
}