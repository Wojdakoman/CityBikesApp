package com.example.citybikesapp.view

import android.app.AlertDialog
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
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.citybikesapp.R
import com.example.citybikesapp.model.entity.Station
import com.example.citybikesapp.viewmodel.BasicViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapFragment : Fragment() {

    private lateinit var basicViewModel: BasicViewModel

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private val PERMISSION_ID: Int = 1010
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val stationsNearby: ArrayList<Station> = arrayListOf()
    private var isMapReady: Boolean = false

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap

    private val locationCallback = object: LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation

            latitude = lastLocation.latitude
            longitude = lastLocation.longitude
            val currentLoc = LatLng(lastLocation.latitude, lastLocation.longitude)
            googleMap.addMarker(MarkerOptions().position(currentLoc).title("My position"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 10f))
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
                        val currentLoc = LatLng(location.latitude, location.longitude)
                        googleMap.addMarker(MarkerOptions().position(currentLoc).title("My position"))
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 10f))
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

    private fun isStationNearby(stationLongitude: Double, stationLatitude: Double): Boolean{
        return Math.abs(stationLatitude - latitude) < 0.15 && Math.abs(stationLongitude - longitude) < 0.15
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            isMapReady = true

            requestPermission()
            getLastLocation()
            basicViewModel.setAPIResponse()

            basicViewModel.apiResponse.observe(viewLifecycleOwner, {
                var temp: Int = 0
                for (i in basicViewModel.apiResponse.value?.networks!!) {
                    if (isStationNearby(stationLatitude = i.location.latitude, stationLongitude = i.location.longitude)) {
                        basicViewModel.getNetworkInfo(i.href)
                        temp += 1
                    }
                }

                if (temp == 0)
                    Toast.makeText(context, "There is no stations in yours location", Toast.LENGTH_SHORT).show()
            })

            basicViewModel.network.observe(viewLifecycleOwner) {
                for (i in basicViewModel.network.value?.network?.stations!!) {
                    stationsNearby.add(i)
                    val location = LatLng(i.latitude, i.longitude)
                    val markerTitle: String = i.name + "\nAddress: " + i.extra.address + "\nEmpty slots: " + i.emptySlots
                    googleMap.addMarker(MarkerOptions().position(location).title(markerTitle))
                }
            }

            googleMap.setOnMarkerClickListener{

                val info: TextView = TextView(context).apply {
                    text = it.title
                    setPadding(16, 0, 0, 0)
                    textSize = 15.0f
                }

                val dialog = AlertDialog.Builder(context).apply {
                    setTitle("Station Information:")
                    setView(info)
                    setPositiveButton("CLOSE"){dialog, _ -> dialog.dismiss()}
                }

                dialog.show()

                true
            }
        })

        val floatingButton = view.findViewById<FloatingActionButton>(R.id.getLocationButton)

        floatingButton.setOnClickListener{
            if (isMapReady){
                googleMap.clear()
                stationsNearby.clear()
                requestPermission()
                getLastLocation()

                var temp: Int = 0
                for (i in basicViewModel.apiResponse.value?.networks!!) {
                    if (isStationNearby(stationLatitude = i.location.latitude, stationLongitude = i.location.longitude)) {
                        basicViewModel.getNetworkInfo(i.href)
                        temp += 1
                    }
                }

                if (temp == 0)
                    Toast.makeText(context, "There is no stations in yours location", Toast.LENGTH_SHORT).show()


            }
        }
    }
}