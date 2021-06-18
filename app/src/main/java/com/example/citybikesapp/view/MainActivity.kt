package com.example.citybikesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.citybikesapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set up navigation
        navController = findNavController(R.id.hostFragment)
        bottom_navigation.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController)

        //toolbar titles handler
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id == R.id.cityDetailFragment){
                supportActionBar?.title = arguments?.get("cityName").toString()
            } else if(destination.id == R.id.stationDetailFragment){
                supportActionBar?.title = arguments?.get("stationName").toString()
            }
            /*supportActionBar?.title = when(destination.id){
                R.id.cityDetailFragment -> arguments?.get("cityName").toString()
                else -> getString(R.string.app_name)
            }*/
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}