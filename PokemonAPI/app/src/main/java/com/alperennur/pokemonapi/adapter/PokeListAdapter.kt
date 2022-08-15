package com.alperennur.pokemonapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.alperennur.pokemonapi.R
import com.alperennur.pokemonapi.databinding.PokelistAdapterBinding
import com.alperennur.pokemonapi.model.Result
import com.alperennur.pokemonapi.model.Type
import com.alperennur.pokemonapi.util.PokemonTypeUtils
import com.alperennur.pokemonapi.util.downloadFromUrl
import com.alperennur.pokemonapi.view.ListFragment
import com.alperennur.pokemonapi.view.ListFragmentDirections
import com.alperennur.pokemonapi.viewmodel.DetailsViewModel
import com.alperennur.pokemonapi.viewmodel.ListViewModel
import java.util.*
import kotlin.collections.ArrayList


class PokeListAdapter(val pokemonList: ArrayList<Result>, val color : ArrayList<Type>) : RecyclerView.Adapter<PokeListAdapter.PokeViewHolder>(),Filterable {

    var pokeFilterList = ArrayList<Result>()
    val fragment = ListFragment()
    init {
        pokeFilterList=pokemonList
    }

    class PokeViewHolder (val binding : PokelistAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {

        val layoutinflater = LayoutInflater.from(parent.context)
        val recItemBinding = PokelistAdapterBinding.inflate(layoutinflater,parent,false)
        return PokeViewHolder(recItemBinding)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {

     //   holder.setIsRecyclable(false)

        holder.binding.apply {
            val pokemon= pokeFilterList[position]
            listName.text=pokemon.name
            listImage.downloadFromUrl(pokemon.getImageURL(),holder.itemView.context)
            holder.itemView.invalidate()
        }


        holder.itemView.setOnClickListener {

            val extras = FragmentNavigatorExtras(holder.binding.listImage to "shared_element_container")
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(pokeFilterList[position].name)
            Navigation.findNavController(it).navigate(action,extras)
        }




    }

    override fun getItemCount(): Int {

        return pokeFilterList.size

    }

    fun updatePokemonList(newPokeList:ArrayList<Result>){

        pokemonList.clear()
        pokemonList.addAll(newPokeList)
        notifyDataSetChanged()
    }


  fun observeColor(new_color :ArrayList<Type>){
      color.clear()
      color.addAll(new_color)
      notifyDataSetChanged()

  }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if (charSearch.isEmpty()){
                    pokeFilterList=pokemonList
                }else{
                    val resultList= ArrayList<Result>()
                    for (row in pokemonList){
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))){
                            resultList.add(row)
                        }
                    }
                    pokeFilterList=resultList
                }
                val filterResults = FilterResults()
                filterResults.values=pokeFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                pokeFilterList= p1?.values as ArrayList<Result>
                notifyDataSetChanged()
            }


        }

    }


}