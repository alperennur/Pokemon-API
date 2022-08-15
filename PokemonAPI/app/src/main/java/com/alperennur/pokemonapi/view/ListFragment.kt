package com.alperennur.pokemonapi.view

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.alperennur.pokemonapi.R
import com.alperennur.pokemonapi.databinding.FragmentListBinding
import com.alperennur.pokemonapi.viewmodel.ListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialElevationScale


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
     lateinit var viewModel : ListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(true)
        reenterTransition = MaterialElevationScale(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refreshData()
        binding.recyclerView.layoutManager=GridLayoutManager(context,2)
        binding.recyclerView.adapter=viewModel.adapter
        badgeCount()

        //SEARCH

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.adapter.filter.filter(p0)
                return false
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
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