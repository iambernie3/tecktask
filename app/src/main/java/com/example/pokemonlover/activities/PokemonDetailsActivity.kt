package com.example.pokemonlover.activities

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aemerse.slider.model.CarouselItem
import com.example.pokemonlover.R
import com.example.pokemonlover.apputil.ConstantData
import com.example.pokemonlover.apputil.GsonUtil
import com.example.pokemonlover.apputil.StringUtil
import com.example.pokemonlover.databinding.ActivityPokemonDetailsBinding
import com.example.pokemonlover.factories.PokemonFactories
import com.example.pokemonlover.interfaces.IEffectsNotify
import com.example.pokemonlover.interfaces.IPokemonResponse
import com.example.pokemonlover.models.AbilitiesModel
import com.example.pokemonlover.models.EffectModel
import com.example.pokemonlover.models.PokemonModel
import com.example.pokemonlover.models.SpritesModel
import com.example.pokemonlover.viewmodels.PokemonVM
import com.example.pokemonlover.volley.VolleyRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject

class PokemonDetailsActivity : AppCompatActivity(), IPokemonResponse,IEffectsNotify {

    companion object {
        private lateinit var binding:ActivityPokemonDetailsBinding
        private lateinit var volleyRequest: VolleyRequest
        private lateinit var pokemonVM: PokemonVM
        private lateinit var myDialog: MaterialAlertDialogBuilder
        private lateinit var alert: AlertDialog
        private var pokemonName:String = ""
        private var effectList: ArrayList<EffectModel> = ArrayList()
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
            pokemonName = intent.getStringExtra("pokemon").toString()
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

            //Display abilities
            if (strAbilities != null) {
                populateAbilities(strAbilities)
            }

            // Display pokemon effects
            fetchEffects(listAbilities)

            // Get Sprites
            val jsonObjectSprites = jsonData.getJSONObject("sprites")
            val spritesModel = GsonUtil().getJsonModel(jsonObjectSprites.toString(),SpritesModel::class.java)
            if (spritesModel != null) {
                preparePokemonImage(spritesModel)
            }
        }
    }

    private fun populateAbilities(abilities:String){
        binding.tvAbilities.text = abilities
    }

    private fun preparePokemonImage(model:SpritesModel) {
        binding.carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()
        list.add(
            CarouselItem(
                imageUrl = model.backDefault,
                caption = "back face"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = model.backShiny,
                caption = "back shiny face"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = model.frontDefault,
                caption = "front face"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = model.frontShiny,
                caption = "front shiny face"
            )
        )

        binding.carousel.setData(list)
    }

    private fun fetchEffects(list: ArrayList<AbilitiesModel>?){
        if (list != null) {
            var effectCtr = 0
            for(l in list) {
                effectCtr+=1
                volleyRequest.getRequest(l.url,object: IPokemonResponse {
                    override fun pokemonResponse(statusCode: String, data: String) {
                        if(ConstantData.STATUS_CODE_OK == statusCode) {
                            val jsonData = JSONObject(data)
                            //Get effect
                            val jsonArrayEffects = jsonData.getJSONArray("effect_entries")
                            for(i in 0 until jsonArrayEffects.length()) {
                                val jObject = jsonArrayEffects.getJSONObject(i)
                                val languageObj = jObject.getJSONObject("language")
                                val lang = languageObj.getString("name")
                                if("en" == lang){
                                  val model = GsonUtil().getJsonModel(jObject.toString(),EffectModel::class.java)
                                    if (model != null) {
                                        effectList.add(model)
                                        if (effectCtr == list.size){
                                            this@PokemonDetailsActivity.effectNotified(true)
                                        }
                                    }
                                }
                            }
                        }
                    }
                })
            }
        }
    }

    override fun effectNotified(isDone: Boolean) {
        if(isDone){
            val strEffects = StringUtil().getStringEffects(effectList)
            binding.tvEffect.text = strEffects
        }
    }
}