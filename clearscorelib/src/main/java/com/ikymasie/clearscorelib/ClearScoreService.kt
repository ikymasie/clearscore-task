package com.ikymasie.clearscorelib

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClearScoreService {
    // endpoint url
    val baseUrl = "https://android-interview.s3.eu-west-2.amazonaws.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}