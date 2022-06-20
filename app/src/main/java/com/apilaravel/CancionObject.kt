package com.apilaravel

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val url="http://tecnoprofe.com/"

object CancionObject {

    fun ApiAdapter(): CancionApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(CancionApi::class.java)
    }
}