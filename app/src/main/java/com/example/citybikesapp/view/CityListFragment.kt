package com.example.citybikesapp.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citybikesapp.R
import com.example.citybikesapp.model.entity.APIResponse
import com.example.citybikesapp.model.entity.Location
import com.example.citybikesapp.viewmodel.BasicViewModel
import com.example.citybikesapp.viewmodel.adapter.CityListAdapter
import kotlinx.android.synthetic.main.fragment_city_list.*

class CityListFragment: Fragment() {
    private lateinit var basicViewModel: BasicViewModel
    private val locationList = mutableListOf<Location>() //lista location
    private val locationWithMatchingName = mutableListOf<Location>()
    private val adapter = CityListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_list, container, false)

        basicViewModel = ViewModelProvider(this).get(BasicViewModel::class.java)
        basicViewModel.setAPIResponse() //pobranie i ustawienie zawartości API jako APIResponse

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //obserwowanie odpowiedzi z API
        basicViewModel.apiResponse.observe(viewLifecycleOwner, Observer { item ->
            //dodanie wszystkich network z odpowiedzi z API do listy
            locationList.clear()
            for (x in item.networks){
                x.location.href = x.href
                locationList.add(x.location)
            }
            locationList.sortBy{it.city}
            locationList.sortBy{it.country} //sortowanie po kraju i mieście
            setAdapter() //przekazanie danych do adaptera
        })
    }

    private fun setAdapter(){
        //ustawienia adaptera i recyclerView
        val recyclerView = cityListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        adapter.setData(locationList) //przekazanie danych do adaptera
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.btnSearch)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                for(x in locationList){
                    if(x.city.toLowerCase().contains(query!!.toLowerCase())){
                        locationWithMatchingName.add(x)
                    }
                }
                adapter.setData(locationWithMatchingName)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                locationWithMatchingName.clear()
                for(x in locationList){
                    if(x.city.toLowerCase().contains(newText!!.toLowerCase())){
                        locationWithMatchingName.add(x)
                    }
                }
                adapter.setData(locationWithMatchingName)
                return true
            }
        })
    }
}