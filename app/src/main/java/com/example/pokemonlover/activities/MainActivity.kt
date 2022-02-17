package com.example.pokemonlover.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonlover.apputil.ConstantData
import com.example.pokemonlover.databinding.ActivityMainBinding
import com.example.pokemonlover.factories.PokemonFactories
import com.example.pokemonlover.interfaces.IPokemonResponse
import com.example.pokemonlover.viewmodels.PokemonVM
import com.example.pokemonlover.volley.VolleyRequest
import org.json.JSONException

class MainActivity : AppCompatActivity(),IPokemonResponse {
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

        val pokemonFactory = PokemonFactories()
        pokemonVM = ViewModelProvider(this,pokemonFactory)[PokemonVM::class.java]

        volleyRequest = VolleyRequest.getInstance(this)
        pokemonVM.fetchPokemon(volleyRequest,this)

    }

    override fun pokemonResponse(statusCode: String, data: String) {
        if(ConstantData.STATUS_CODE_OK == statusCode) {
            try {

            }catch (e:JSONException){
                e.printStackTrace()
                Log.e(TAG,"exception: ${e.message}")
            }
        }
    }
}