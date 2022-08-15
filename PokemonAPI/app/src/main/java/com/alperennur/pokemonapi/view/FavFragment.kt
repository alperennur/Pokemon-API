package com.alperennur.pokemonapi.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.alperennur.pokemonapi.R
import com.alperennur.pokemonapi.adapter.FavAdapter
import com.alperennur.pokemonapi.databinding.FragmentFavBinding
import com.alperennur.pokemonapi.model.Result
import com.alperennur.pokemonapi.viewmodel.DetailsViewModel
import com.alperennur.pokemonapi.viewmodel.FavViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialElevationScale
import java.lang.Exception


class FavFragment : Fragment() {

    private lateinit var binding : FragmentFavBinding
    var adapter = FavAdapter(arrayListOf())
    lateinit var viewmodel_fav : FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)
        dataBase()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.favRecy.layoutManager=GridLayoutManager(context,2)
        binding.favRecy.adapter=adapter
        badgeCount()

    }

    override fun onResume() {
        super.onResume()
    }

fun dataBase(){

    try {
        val database = activity?.openOrCreateDatabase("Pokedex",Context.MODE_PRIVATE,null)
        val cursor = database?.rawQuery("SELECT * FROM pokedex",null)
        val name_index = cursor?.getColumnIndex("name")
        val url_index = cursor?.getColumnIndex("url")

        while (cursor?.moveToNext()!!) {

            val passData = Result("${cursor.getString(name_index!!)}","${cursor.getString(url_index!!)}")
            adapter.updateFavList(passData)

        }
                cursor?.close()

    }catch (e:Exception){

        e.printStackTrace()
    }

}

    fun badgeCount(){

        val database = activity?.openOrCreateDatabase("Pokedex", Context.MODE_PRIVATE, null)
        database?.execSQL("CREATE TABLE IF NOT EXISTS pokedex (name VARCHAR , url VARCHAR , cont INTEGER)")
        val cursor = database?.rawQuery("SELECT * FROM pokedex",null)
        val badge=activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        badge?.getOrCreateBadge(R.id.favFragment)?.number=cursor?.count!!
        cursor.close()
    }

}