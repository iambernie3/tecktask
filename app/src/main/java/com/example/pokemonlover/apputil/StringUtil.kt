package com.example.pokemonlover.apputil

import com.example.pokemonlover.models.AbilitiesModel
import com.example.pokemonlover.models.EffectModel

class StringUtil {
    fun getStringAbilities(arrayList: ArrayList<AbilitiesModel>): String? {
        val builder = StringBuilder()
        for( a in arrayList) {
            builder.append("- ${a.name} \n")
        }

        return builder.toString()
    }

    fun getStringEffects(arrayList: ArrayList<EffectModel>): String?{
        val builder = StringBuilder()
        for( a in arrayList) {
            builder.append("- ${a.effect} \n")
        }

        return builder.toString()
    }
}