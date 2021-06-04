package com.example.citybikesapp.view

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.citybikesapp.R
import com.example.citybikesapp.viewmodel.BasicViewModel
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment() {

    private lateinit var basicViewModel: BasicViewModel

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private val PERMISSION_ID: Int = 1010
    private val MAX_DISTANCE: Int = 1000
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private val locationCallback = object: LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation

            latitude = lastLocation.latitude
            longitude = lastLocation.longitude
        }
    }

    private fun checkPermission(): Boolean =
        (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

    private fun requestPermission(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)
    }

    private fun isLocationEnabled(): Boolean{
        var locationManager: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null)
                        newLocationData()
                    else{
                        longitude = location.longitude
                        latitude = location.latitude
                    }

                }
            }
            else{
                Toast.makeText(context, "Please turn on GPS on Your device", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            requestPermission()
        }
    }

    private fun newLocationData(){
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if(checkPermission())
            fusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private fun rad(x: Double) = (x * Math.PI) / 180

    private fun isStationNearby(stationLongitude: Double, stationLatitude: Double): Boolean{
        return Math.abs(stationLatitude - latitude) < 0.1 && Math.abs(stationLongitude - longitude) < 0.1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        basicViewModel = ViewModelProvider(requireActivity()).get(BasicViewModel::class.java)
        basicViewModel.setAPIResponse()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        basicViewModel.apiResponse.observe(viewLifecycleOwner, {
            basicViewModel.apiResponse.value.let {
                var text: String = ""
                for(i in basicViewModel.apiResponse.value?.networks!!){
                    if(isStationNearby(stationLatitude = i.location.latitude, stationLongitude = i.location.longitude))
                        text += isStationNearby(stationLatitude = i.location.latitude, stationLongitude = i.location.longitude).toString() + " " + i.location.city + " " + i.location.latitude.toString() + " " + i.location.longitude.toString() + " | " + latitude.toString() + " " + longitude.toString() + "\n"
                }

                var testTV = view.findViewById<TextView>(R.id.test)
                testTV.text = text
            }
        })

        requestPermission()
        getLastLocation()
    }
}