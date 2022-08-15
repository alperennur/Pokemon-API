package com.alperennur.pokemonapi.service

import com.alperennur.pokemonapi.model.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class PokemonAPIService {

    private val BASE_URL = "https://pokeapi.co/api/v2/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PokemonAPI::class.java)

fun getData() : Call<Model> {

    return api.getPokemon(30,0)

}
    fun getImages(name: String) : Call<Abilities>{

        return api.getImage(name)

    }
}