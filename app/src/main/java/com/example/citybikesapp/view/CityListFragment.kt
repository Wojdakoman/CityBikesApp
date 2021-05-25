package com.example.citybikesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val locationList = mutableListOf<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_list, container, false)

        basicViewModel = ViewModelProvider(this).get(BasicViewModel::class.java)

        basicViewModel.setAPIResponse()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        basicViewModel.apiResponse.observe(viewLifecycleOwner, Observer { item ->
            for (x in item.networks){
                locationList.add(x.location)
            }
            locationList.sortBy{it.country}
            setAdapter()
        })
    }

    private fun setAdapter(){
        val adapter = CityListAdapter()
        val recyclerView = cityListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        adapter.setData(locationList)
    }

}