package com.example.pokemonlover.interfaces

import android.graphics.Bitmap

interface IImageRequest {
    fun onImageResponse(statusCode:String, bitmap: Bitmap?)
}