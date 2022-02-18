package com.example.pokemonlover.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonlover.R
import com.example.pokemonlover.adapters.PokemonListAdapter
import com.example.pokemonlover.apputil.ConnectionChecker
import com.example.pokemonlover.apputil.ConstantData
import com.example.pokemonlover.apputil.GsonUtil
import com.example.pokemonlover.databinding.ActivityMainBinding
import com.example.pokemonlover.factories.PokemonFactories
import com.example.pokemonlover.interfaces.IPokemonResponse
import com.example.pokemonlover.interfaces.IPokemonSelected
import com.example.pokemonlover.models.PokemonModel
import com.example.pokemonlover.viewmodels.PokemonVM
import com.example.pokemonlover.volley.VolleyRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject

class MainActivity : AppCompatActivity(),IPokemonResponse,IPokemonSelected {
    companion object{
        private var TAG = "MainActivity"
        private lateinit var binding:ActivityMainBinding
        private lateinit var pokemonVM: PokemonVM
        @SuppressLint("StaticFieldLeak")
        private lateinit var volleyRequest: VolleyRequest
        private lateinit var myDialog: MaterialAlertDialogBuilder
        private lateinit var alert: AlertDialog

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

        prepareDialog()
        val pokemonFactory = PokemonFactories()
        pokemonVM = ViewModelProvider(this,pokemonFactory)[PokemonVM::class.java]

        volleyRequest = VolleyRequest.getInstance(this)

        if(ConnectionChecker.isConnectedToNetwork(this)){
            alert.show()
            pokemonVM.fetchPokemon(volleyRequest,this)
        }else{
            ConnectionChecker.showOffline(this)
        }


    }

    private fun prepareDialog(){
        myDialog = MaterialAlertDialogBuilder(this)
        myDialog.setView(R.layout.progress_bar_layout_please_wait)
        myDialog.background = resources.getDrawable(R.color.gray,null)
        alert = myDialog.create()
    }

    private fun setPokemonAdapter(list:List<PokemonModel>) {
        val adapter = PokemonListAdapter(this)
        binding.rcyPokemon.setHasFixedSize(true)
        //binding.rcyPokemon.layoutManager = GridLayoutManager(this,2)
        binding.rcyPokemon.adapter = adapter
        adapter.setAdapter(list,this)
    }

    override fun pokemonResponse(statusCode: String, data: String) {
        alert.dismiss()
        if(ConstantData.STATUS_CODE_OK == statusCode) {
            val jsonData = JSONObject(data)
            val jsonArray = jsonData.getJSONArray("results")
            val list = GsonUtil.jsonToArrayList(jsonArray.toString(),PokemonModel::class.java)
            if (list != null) {
                setPokemonAdapter(list)
            }
        }else{
            ConnectionChecker.showResponseMessage(this,data)
        }
    }

    override fun onSelectedPokemon(model: PokemonModel) {
        val intent = Intent(this,PokemonDetailsActivity::class.java)
        intent.putExtra("pokemon",model.name)
        intent.putExtra("url",model.url)
        startActivity(intent)
    }
}