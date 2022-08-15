package com.alperennur.pokemonapi.util

import com.alperennur.pokemonapi.R

object PokemonTypeUtils {

    fun getTypeColor(type: String): String {
        return when (type) {
            "fighting" -> "#90B1C5"
            "flying" -> "#90B1C5"
            "poison" -> "#9F422A"
            "ground" -> "#AD7235"
            "rock" -> "#4B190E"
            "bug" -> "#179A55"
            "ghost" -> "#363069"
            "steel" -> "#5C756D"
            "fire" -> "#B22328"
            "water" -> "#0091EA"
            "grass" -> "#81C784"
            "electric" -> "#FFD600"
            "psychic" -> "#AC296B"
            "ice" -> "#7ECFF2"
            "dragon" -> "#378A94"
            "fairy" -> "#9E1A44"
            "dark" -> "#040706"
            else -> "#B1A5A5"
        }
    }

}