package com.alperennur.pokemonapi.model


import com.google.gson.annotations.SerializedName

data class EvolutionChain(
    @SerializedName("url")
    val url: String
)