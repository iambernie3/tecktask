package com.example.pokemonlover.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonlover.R
import com.example.pokemonlover.interfaces.IPokemonSelected
import com.example.pokemonlover.models.PokemonModel

class PokemonListAdapter(val context: Context): RecyclerView.Adapter<PokemonListAdapter.PokemonListVH>() {

    private var listPokemon:List<PokemonModel> = emptyList()
    private var pokemonSelectItem:IPokemonSelected? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonListVH {
        return PokemonListVH(LayoutInflater.from(context).inflate(R.layout.pokemon_list_adapter_layout,parent,false))
    }

    override fun onBindViewHolder(holder: PokemonListVH, position: Int) {
        val data = listPokemon[position]
        holder.tvPokemonName.text = data.name
        holder.view.setOnClickListener {
            pokemonSelectItem?.onSelectedPokemon(data)
        }
    }

    override fun getItemCount(): Int {
        return listPokemon.size
    }

    fun setAdapter(listPokemon:List<PokemonModel>,pokemonSelectItem:IPokemonSelected) {
        this.listPokemon = listPokemon
        this.pokemonSelectItem = pokemonSelectItem
        notifyDataSetChanged()
    }

    class PokemonListVH(view: View):RecyclerView.ViewHolder(view) {
        val tvPokemonName:TextView = view.findViewById(R.id.tv_pokemon_name)
        val view = view
    }
}