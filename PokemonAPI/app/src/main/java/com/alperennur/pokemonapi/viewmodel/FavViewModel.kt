package com.alperennur.pokemonapi.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.alperennur.pokemonapi.model.Result
import com.alperennur.pokemonapi.view.FavFragment
import java.lang.Exception

class FavViewModel : ViewModel() {

    val fragment = FavFragment()

    fun dataBase(){

        try {
            val database = fragment.activity?.openOrCreateDatabase("Pokedex", Context.MODE_PRIVATE,null)
            val cursor = database?.rawQuery("SELECT * FROM pokedex",null)
            val name_index = cursor?.getColumnIndex("name")
            val url_index = cursor?.getColumnIndex("url")

            while (cursor?.moveToNext()!!) {

                val passData = Result("${cursor.getString(name_index!!)}","${cursor.getString(url_index!!)}")
                fragment.adapter.updateFavList(passData)

            }
            cursor?.close()

        }catch (e: Exception){

            e.printStackTrace()
        }


    }


}