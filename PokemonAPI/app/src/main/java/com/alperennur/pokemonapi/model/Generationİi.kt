package com.alperennur.pokemonapi.model


import com.google.gson.annotations.SerializedName

data class Generationİi(
    @SerializedName("crystal")
    val crystal: Crystal,
    @SerializedName("gold")
    val gold: Gold,
    @SerializedName("silver")
    val silver: Silver
)