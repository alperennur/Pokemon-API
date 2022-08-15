package com.alperennur.pokemonapi.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld,
    @SerializedName("home")
    val home: Home,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
):Serializable