package com.alperennur.pokemonapi.service

import com.alperennur.pokemonapi.model.Abilities
import com.alperennur.pokemonapi.model.DreamWorld
import com.alperennur.pokemonapi.model.Model
import com.alperennur.pokemonapi.model.OfficialArtwork
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon?")

    fun getPokemon(@Query("limit") limit:Int,@Query("offset") offset:Int): Call<Model>

    @GET("pokemon/{name}")

    fun getImage(@Path("name") name:String) : Call<Abilities>

    @GET("pokemon?")
    fun getSingleData(@Query("limit") limit:Int,@Query("offset") offset:Int): Single<List<Model>>

}


//https://pokeapi.co/api/v2/pokemon/1
// https://pokeapi.co/api/v2/pokemon/2
