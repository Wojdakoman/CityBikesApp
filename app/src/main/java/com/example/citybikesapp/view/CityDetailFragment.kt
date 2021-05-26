package com.example.citybikesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.citybikesapp.R
import com.example.citybikesapp.viewmodel.BasicViewModel
import kotlinx.android.synthetic.main.fragment_city_detail.*

class CityDetailFragment: Fragment() {
    private val args by navArgs<CityDetailFragmentArgs>()
    private lateinit var basicViewModel: BasicViewModel

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityCountryString = args.location.city + ", " + args.location.country
        cityNameCountryCode.text = cityCountryString
        latitudeValue.text = args.location.latitude.toString()
        longitudeValue.text = args.location.longitude.toString()

    }
}