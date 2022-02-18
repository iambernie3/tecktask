package com.example.pokemonlover.apputil

import android.text.TextUtils
import android.util.Log
import com.example.pokemonlover.models.PokemonModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type


class GsonUtil {

    fun <T> jsonToArrayList(json: String?, clazz: Class<T>?): ArrayList<T>? {
        if (TextUtils.isEmpty(json)) {
            return null
        }

        val type: Type = object : TypeToken<ArrayList<JsonObject?>?>() {}.type
        val jsonObjects: ArrayList<JsonObject> = Gson().fromJson(json, type)
        val arrayList: ArrayList<T> = ArrayList()
        for (jsonObject in jsonObjects) {
            arrayList.add(Gson().fromJson(jsonObject, clazz))
        }
        return arrayList
    }

    fun <T> jsonToArrayListAbilities(json: String?, clazz: Class<T>?): ArrayList<T>? {
        if (TextUtils.isEmpty(json)) {
            return null
        }

        val type: Type = object : TypeToken<ArrayList<JsonObject?>?>() {}.type
        val jsonObjects: ArrayList<JsonObject> = Gson().fromJson(json, type)
        val arrayList: ArrayList<T> = ArrayList()
        for (jsonObject in jsonObjects) {
            arrayList.add(Gson().fromJson(jsonObject.getAsJsonObject("ability"), clazz))
        }
        return arrayList
    }
}