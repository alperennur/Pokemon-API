package com.alperennur.pokemonapi.viewmodel

import android.animation.ObjectAnimator
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.alperennur.pokemonapi.adapter.PokeListAdapter
import com.alperennur.pokemonapi.model.*
import com.alperennur.pokemonapi.service.PokemonAPIService
import com.alperennur.pokemonapi.util.downloadFromUrl
import com.alperennur.pokemonapi.view.DetailsFragment
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel: ViewModel(){

    private val apiService= PokemonAPIService()
    val abilities = MutableLiveData<Abilities>()

    fun detailsData(name:String){

        apiService.getImages(name).enqueue(
            object : Callback<Abilities> {
                override fun onResponse(call: Call<Abilities>, response: Response<Abilities>) {

                    abilities.value=response.body()
                }
                override fun onFailure(call: Call<Abilities>, t: Throwable) {
                }
            }
        )

    }

}





