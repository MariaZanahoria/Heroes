package com.wizeline.heroes

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {

    fun getServices(): HeroesServices {
        return Retrofit.Builder().apply {
            baseUrl("https://gateway.marvel.com/v1/public/")
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build().create(HeroesServices::class.java)
    }
}
