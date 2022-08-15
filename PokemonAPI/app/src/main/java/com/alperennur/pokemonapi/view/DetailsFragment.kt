package com.alperennur.pokemonapi.view

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.alperennur.pokemonapi.R
import com.alperennur.pokemonapi.adapter.FavAdapter
import com.alperennur.pokemonapi.databinding.FragmentDetailsBinding
import com.alperennur.pokemonapi.model.*
import com.alperennur.pokemonapi.util.PokemonTypeUtils
import com.alperennur.pokemonapi.util.downloadFromUrl
import com.alperennur.pokemonapi.viewmodel.DetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception
import java.lang.invoke.ConstantCallSite


class DetailsFragment: Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private var uuid : String=""
    lateinit var viewModel_details : DetailsViewModel
    var details_list : ArrayList<Stat> = arrayListOf()
    val progressBarList : ArrayList<ProgressBar> = arrayListOf()
    private lateinit var type: List<Type>
    val result = MutableLiveData<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel_details = ViewModelProvider(this).get(DetailsViewModel::class.java)
        addProgressBar()

        arguments?.let {

            uuid = DetailsFragmentArgs.fromBundle(it).uuid
            viewModel_details.detailsData(uuid)
        }
        observer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
   fun observer(){

       viewModel_details.abilities.observe(viewLifecycleOwner){

           val url = it?.sprites?.other?.home?.frontDefault.toString()
           val height_value= it?.height
           val weight_value = it?.weight
           var pokemon_name = it?.name
           var details = it?.stats
           type=it.types
           binding.linearLayout.background.setTint(Color.parseColor(PokemonTypeUtils.getTypeColor(type[0].type.name)))

           if (type.size<2){

               binding.skillOne.text=type[0].type.name
               binding.skillOne.background.setTint(Color.parseColor(PokemonTypeUtils.getTypeColor(type[0].type.name)))
               binding.skillTwo.visibility=View.GONE
           }
           else{
               binding.skillOne.text=type[0].type.name
               binding.skillOne.background.setTint(Color.parseColor(PokemonTypeUtils.getTypeColor(type[0].type.name)))
               binding.skillTwo.text=type[1].type.name
               binding.skillTwo.background.setTint(Color.parseColor(PokemonTypeUtils.getTypeColor(type[1].type.name)))

           }
           context?.let {
               binding.pokemonImage.downloadFromUrl(url,it)
           }
           binding.pokemonHeightValue.text= "$height_value M"
           binding.pokemonWeightValue.text= "$weight_value KG"
           binding.pokemonName.text= pokemon_name.toString()
           details_list = details as ArrayList<Stat>


           for (i in 0..4){
               val currentProgress = details_list[i].baseStat
               ObjectAnimator.ofInt(progressBarList[i],"progress",currentProgress)
                   .setDuration(1000)
                   .start()
           }
           addOrDelete(url)
           checkToggle()

       }
   }
fun addProgressBar(){

    progressBarList.add(binding.barHp)
    progressBarList.add(binding.barAtk)
    progressBarList.add(binding.barDef)
    progressBarList.add(binding.barSpd)
    progressBarList.add(binding.barExp)
}

    fun addOrDelete(url: String){

        binding.favButton.setOnCheckedChangeListener { compoundButton, b ->

            if (b) {
                result.value = binding.pokemonName.text.toString()
                try {

                    var names = binding.pokemonName.text
                    val database =
                        activity?.openOrCreateDatabase("Pokedex", Context.MODE_PRIVATE, null)
                    database?.execSQL("CREATE TABLE IF NOT EXISTS pokedex (name VARCHAR , url VARCHAR , cont INTEGER)")

                     val control = database?.rawQuery("SELECT * FROM pokedex WHERE name = '$names'",null)

                    if (control?.count==0){

                        database?.execSQL("INSERT INTO pokedex (name,url,cont) VALUES('$names','$url',1)")
                        Toast.makeText(context,"added to favorites",Toast.LENGTH_SHORT).show()
                    }
           //             database?.execSQL("DELETE FROM pokedex")

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else{
                var names = binding.pokemonName.text
                val datebase =
                    activity?.openOrCreateDatabase("Pokedex", Context.MODE_PRIVATE, null)
              //  datebase?.execSQL("CREATE TABLE IF NOT EXISTS pokemon (name VARCHAR)")
                datebase?.execSQL("DELETE FROM pokedex WHERE name='$names' ")
                Toast.makeText(context,"remove from favorites",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkToggle(){

        var names = binding.pokemonName.text
        try {
            val database =
                activity?.openOrCreateDatabase("Pokedex", Context.MODE_PRIVATE, null)
            val cursor = database?.rawQuery("SELECT * FROM pokedex",null)
            val index = cursor?.getColumnIndex("name")
            while (cursor?.moveToNext()!!){

                if (cursor?.getString(index!!)==names){
                    binding.favButton.isChecked=true
                }
            }
            cursor?.close()

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

}