package com.example.pokemonlover.volley

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.android.volley.*
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.example.pokemonlover.apputil.ConstantData
import com.example.pokemonlover.interfaces.IImageRequest
import com.example.pokemonlover.interfaces.IPokemonResponse

class VolleyRequest(private val context: Context) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: VolleyRequest? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VolleyRequest(context).also {
                    INSTANCE = it
                }
            }
    }

    fun getRequest(url:String,listener:IPokemonResponse){
        val queue = VolleySingleton.getInstance(context)
        val stringReq = object: StringRequest(
            Method.GET,url, Response.Listener { response -> listener.pokemonResponse(ConstantData.STATUS_CODE_OK,response.toString()) },
            Response.ErrorListener { error ->
                val message: String?
                when (error) {
                    is NetworkError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ServerError -> {
                        message = "The server could not be found. Please try again after some time!!"
                    }
                    is AuthFailureError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ParseError -> {
                        message = "Parsing error! Please try again after some time!!"
                    }
                    is NoConnectionError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is TimeoutError -> {
                        message = "Connection TimeOut! Please check your internet connection."
                    }
                    else -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                }

                listener.pokemonResponse(ConstantData.STATUS_CODE_ERROR,message)
            }
        ) {}

        queue.addToRequestQueue(stringReq)
    }

    fun fetchImage(url: String,listener:IImageRequest){
        val queue = VolleySingleton.getInstance(context)
        val request = object : ImageRequest(url,Response.Listener {
            listener.onImageResponse(ConstantData.STATUS_CODE_OK,it)
        },0,0,ImageView.ScaleType.CENTER,null,Response.ErrorListener {
            listener.onImageResponse(ConstantData.STATUS_CODE_ERROR,null)
        }){}
        queue.addToRequestQueue(request)
    }
}