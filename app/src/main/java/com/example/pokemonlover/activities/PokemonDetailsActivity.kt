package com.example.pokemonlover.activities

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonlover.R
import com.example.pokemonlover.apputil.ConstantData
import com.example.pokemonlover.apputil.GsonUtil
import com.example.pokemonlover.apputil.StringUtil
import com.example.pokemonlover.databinding.ActivityPokemonDetailsBinding
import com.example.pokemonlover.factories.PokemonFactories
import com.example.pokemonlover.interfaces.IPokemonResponse
import com.example.pokemonlover.models.AbilitiesModel
import com.example.pokemonlover.models.PokemonModel
import com.example.pokemonlover.viewmodels.PokemonVM
import com.example.pokemonlover.volley.VolleyRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject

class PokemonDetailsActivity : AppCompatActivity(), IPokemonResponse {

    companion object {
        private lateinit var binding:ActivityPokemonDetailsBinding
        private lateinit var volleyRequest: VolleyRequest
        private lateinit var pokemonVM: PokemonVM
        private lateinit var myDialog: MaterialAlertDialogBuilder
        private lateinit var alert: AlertDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = "back"
            actionBar.elevation = 4.0F
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val pokemonFactory = PokemonFactories()
        pokemonVM = ViewModelProvider(this,pokemonFactory)[PokemonVM::class.java]
        volleyRequest = VolleyRequest.getInstance(this)
        prepareDialog()

        if(intent.extras != null) {
          val pokemonName = intent.getStringExtra("pokemon").toString()
          val pokemonAbilitiesUrl = intent.getStringExtra("url").toString()
                binding.tvPokemonName.text = pokemonName
            fetchAbilityAndSprites(pokemonAbilitiesUrl)
        }


    }

    private fun prepareDialog(){
        myDialog = MaterialAlertDialogBuilder(this)
        myDialog.setView(R.layout.progress_bar_layout_please_wait)
        myDialog.background = resources.getDrawable(R.color.gray,null)
        alert = myDialog.create()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out)
        return true
    }

    private fun fetchAbilityAndSprites(url:String){
        volleyRequest.getRequest(url,this)
    }

    //Response for Ability and Sprites
    override fun pokemonResponse(statusCode: String, data: String) {
        if(ConstantData.STATUS_CODE_OK == statusCode) {
            val jsonData = JSONObject(data)
            //Get Abilities
            val jsonArrayAbilities = jsonData.getJSONArray("abilities")

            val listAbilities = GsonUtil().jsonToArrayListAbilities(jsonArrayAbilities.toString(), AbilitiesModel::class.java)
            val strAbilities = listAbilities?.let { StringUtil().getStringAbilities(it) }
            if (strAbilities != null) {
                populateAbilities(strAbilities)
            }

            // Get Sprites
            val jsonObjectSprites = jsonData.getJSONObject("sprites")
        }
    }

    private fun populateAbilities(abilities:String){
        binding.tvAbilities.text = abilities
    }
}