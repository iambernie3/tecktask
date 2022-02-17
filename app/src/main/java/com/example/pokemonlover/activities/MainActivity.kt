package com.example.pokemonlover.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonlover.adapters.PokemonListAdapter
import com.example.pokemonlover.apputil.ConstantData
import com.example.pokemonlover.apputil.PokemonList
import com.example.pokemonlover.databinding.ActivityMainBinding
import com.example.pokemonlover.factories.PokemonFactories
import com.example.pokemonlover.interfaces.IPokemonResponse
import com.example.pokemonlover.interfaces.IPokemonSelected
import com.example.pokemonlover.models.PokemonModel
import com.example.pokemonlover.viewmodels.PokemonVM
import com.example.pokemonlover.volley.VolleyRequest
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(),IPokemonResponse,IPokemonSelected {
    companion object{
        private var TAG = "MainActivity"
        private lateinit var binding:ActivityMainBinding
        private lateinit var pokemonVM: PokemonVM
        @SuppressLint("StaticFieldLeak")
        private lateinit var volleyRequest: VolleyRequest

        init {
             System.loadLibrary("pokemonlover")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = "Pokemon Lover"
            actionBar.elevation = 4.0F
        }

        val pokemonFactory = PokemonFactories()
        pokemonVM = ViewModelProvider(this,pokemonFactory)[PokemonVM::class.java]

        volleyRequest = VolleyRequest.getInstance(this)
        pokemonVM.fetchPokemon(volleyRequest,this)

    }

    private fun setPokemonAdapter(list:List<PokemonModel>) {
        val adapter = PokemonListAdapter(this)
        binding.rcyPokemon.setHasFixedSize(true)
        //binding.rcyPokemon.layoutManager = GridLayoutManager(this,2)
        binding.rcyPokemon.adapter = adapter
        adapter.setAdapter(list,this)
    }

    override fun pokemonResponse(statusCode: String, data: String) {
        if(ConstantData.STATUS_CODE_OK == statusCode) {
            val list = PokemonList().getPokemonList(data)
            setPokemonAdapter(list)
        }
    }

    override fun onSelectedPokemon(model: PokemonModel) {

    }
}