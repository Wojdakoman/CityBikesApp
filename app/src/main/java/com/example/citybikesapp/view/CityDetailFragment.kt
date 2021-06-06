package com.example.citybikesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citybikesapp.R
import com.example.citybikesapp.model.entity.Station
import com.example.citybikesapp.viewmodel.BasicViewModel
import com.example.citybikesapp.viewmodel.adapter.StationListAdapter
import kotlinx.android.synthetic.main.fragment_city_detail.*

class CityDetailFragment: Fragment() {
    private val args by navArgs<CityDetailFragmentArgs>()
    private lateinit var basicViewModel: BasicViewModel
    private var stationList = mutableListOf<Station>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_detail, container, false)

        basicViewModel = ViewModelProvider(this).get(BasicViewModel::class.java)
        basicViewModel.getNetworkInfo(args.location.href.toString())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityCountryString = args.location.city + ", " + args.location.country
        cityNameCountryCode.text = cityCountryString
        freeBikesValue.text = args.location.latitude.toString()
        longitudeValue.text = args.location.longitude.toString()
        basicViewModel.network.observe(viewLifecycleOwner, Observer { item ->
            stationList = item.network.stations as MutableList<Station>
            setAdapter()
        })
    }

    private fun setAdapter(){
        val adapter = StationListAdapter()
        val recyclerView = stationListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        adapter.setData(stationList)
    }
}