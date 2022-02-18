package com.example.pokemonlover.apputil

import android.text.TextUtils
import android.util.Log
import com.example.pokemonlover.models.PokemonModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type


object GsonUtil {

    fun <T> jsonToArrayList(strJsonArray: String?, clazz: Class<T>?): ArrayList<T>? {
        val arrayList: ArrayList<T> = ArrayList()
        if(strJsonArray.isNullOrEmpty()){
            return null
        }

        try {
            val type: Type = object : TypeToken<ArrayList<JsonObject?>?>() {}.type
            val jsonObjects: ArrayList<JsonObject> = Gson().fromJson(strJsonArray, type)
            for (jsonObject in jsonObjects) {
                arrayList.add(Gson().fromJson(jsonObject, clazz))
            }
        }catch (e: JsonSyntaxException){
            return null
        }

        return arrayList
    }

    fun <T> jsonToArrayListAbilities(strJsonObject: String?, clazz: Class<T>?): ArrayList<T>? {
        if (strJsonObject.isNullOrEmpty()) {
            return null
        }

        val type: Type = object : TypeToken<ArrayList<JsonObject?>?>() {}.type
        val jsonObjects: ArrayList<JsonObject> = Gson().fromJson(strJsonObject, type)
        val arrayList: ArrayList<T> = ArrayList()
        for (jsonObject in jsonObjects) {
            arrayList.add(Gson().fromJson(jsonObject.getAsJsonObject("ability"), clazz))
        }
        return arrayList
    }

    fun <T> getJsonModel(strJsonObject:String?,clazz: Class<T>?): T? {
        if (strJsonObject.isNullOrEmpty()) {
            return null
        }

        val type: Type = object : TypeToken<JsonObject?>() {}.type
        val jsonObjects: JsonObject = Gson().fromJson(strJsonObject, type)

        return Gson().fromJson(jsonObjects,clazz)
    }
}