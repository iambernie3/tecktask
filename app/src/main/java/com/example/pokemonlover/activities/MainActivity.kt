package com.example.pokemonlover.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonlover.adapters.PokemonListAdapter
import com.example.pokemonlover.apputil.ConstantData
import com.example.pokemonlover.apputil.GsonUtil
import com.example.pokemonlover.databinding.ActivityMainBinding
import com.example.pokemonlover.factories.PokemonFactories
import com.example.pokemonlover.interfaces.IPokemonResponse
import com.example.pokemonlover.interfaces.IPokemonSelected
import com.example.pokemonlover.models.PokemonModel
import com.example.pokemonlover.viewmodels.PokemonVM
import com.example.pokemonlover.volley.VolleyRequest
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
            val jsonData = JSONObject(data)
            val jsonArray = jsonData.getJSONArray("results")
            val list = GsonUtil().jsonToArrayList(jsonArray.toString(),PokemonModel::class.java)
            if (list != null) {
                setPokemonAdapter(list)
            }
        }
    }

    override fun onSelectedPokemon(model: PokemonModel) {
        val intent = Intent(this,PokemonDetailsActivity::class.java)
        intent.putExtra("pokemon",model.name)
        intent.putExtra("url",model.url)
        startActivity(intent)
    }
}