package com.example.pokemonlover.apputil

import com.example.pokemonlover.models.AbilitiesModel

class StringUtil {
    fun getStringAbilities(arrayList: ArrayList<AbilitiesModel>): String? {
        val builder = StringBuilder()
        for( a in arrayList) {
            builder.append("- ${a.name} \n")
        }

        return builder.toString()
    }
}