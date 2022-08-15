package com.alperennur.pokemonapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.alperennur.pokemonapi.databinding.FavAdapterBinding
import com.alperennur.pokemonapi.model.Result
import com.alperennur.pokemonapi.util.downloadFromUrl
import com.alperennur.pokemonapi.view.FavFragmentDirections
import com.alperennur.pokemonapi.view.ListFragmentDirections

class FavAdapter(val favList :ArrayList<Result>):RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    var adapter=PokeListAdapter(arrayListOf(), arrayListOf())


    class FavViewHolder (val binding :FavAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val favItemBinding = FavAdapterBinding.inflate(layoutInflater,parent,false)
        return FavViewHolder(favItemBinding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {

        holder.binding.apply {

           val fav=favList[position]
            favListName.text=fav.name
            favListImage.downloadFromUrl(fav.url,holder.itemView.context)

        }

        holder.itemView.setOnClickListener {

            val extras = FragmentNavigatorExtras(holder.binding.favListImage to "shared_element_container")
            val action = FavFragmentDirections.actionFavFragmentToDetailsFragment(favList[position].name)
            Navigation.findNavController(it).navigate(action,extras)
        }

    }

    override fun getItemCount(): Int {

        return favList.size
    }

    fun updateFavList(newPokeList:Result){

        favList.add(newPokeList)
        notifyDataSetChanged()
    }

}