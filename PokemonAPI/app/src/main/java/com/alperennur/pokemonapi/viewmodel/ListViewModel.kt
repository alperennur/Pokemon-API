package com.alperennur.pokemonapi.viewmodel
import android.content.Context
import android.widget.Adapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperennur.pokemonapi.adapter.PokeListAdapter
import com.alperennur.pokemonapi.model.*
import com.alperennur.pokemonapi.service.PokemonAPIService
import com.alperennur.pokemonapi.view.ListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListViewModel:ViewModel(){

    private val pokemonApiService = PokemonAPIService()
    var adapter = PokeListAdapter(arrayListOf(), arrayListOf())
    var pokeList : ArrayList<Result> = arrayListOf()
    val abilities = MutableLiveData<Abilities>()

    fun refreshData(){
        getDataFromAPI()

    }

    private fun getDataFromAPI(){

         pokemonApiService.getData().enqueue(
             object : Callback<Model>{
                 override fun onResponse(call: Call<Model>, response: Response<Model>) {

                     val tempList = response.body()?.results
                     tempList?.let {

                         pokeList = it as ArrayList<Result>
                         adapter.updatePokemonList(pokeList)
                     }
                 }
                 override fun onFailure(call: Call<Model>, t: Throwable) {
                 }
             }
         )
    }

    private fun getColorData(){



    }

}



