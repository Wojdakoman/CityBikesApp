package com.example.citybikesapp.viewmodel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.citybikesapp.R
import com.example.citybikesapp.model.entity.Location
import com.example.citybikesapp.model.entity.SavedCity
import com.example.citybikesapp.view.ListsFragmentDirections
import com.example.citybikesapp.viewmodel.ListsViewModel
import kotlinx.android.synthetic.main.city_list_row.view.*
import kotlinx.android.synthetic.main.city_list_row.view.cityName
import kotlinx.android.synthetic.main.saved_city_row.view.*

class SavedCityAdapter(private val viewModel: ListsViewModel): RecyclerView.Adapter<SavedCityAdapter.Holder>() {
    inner class Holder(view: View): RecyclerView.ViewHolder(view)

    var list = listOf<SavedCity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_city_row, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.cityName.text = list[position].city_name
        holder.itemView.countryName.text = list[position].country_name
        holder.itemView.networkName.text = list[position].network_name

        //click element
        holder.itemView.setOnClickListener {
            val action = ListsFragmentDirections.actionListsFragmentToCityDetailFragment(Location(list[position].city_name, list[position].country_name, 0.0, 0.0, "v2/networks/${list[position].network_id}"))
            holder.itemView.findNavController().navigate(action)
        }

        //del btn
        holder.itemView.btnDelete.setOnClickListener {
            viewModel.deleteSavedCity(list[position].network_id)
        }
    }

    override fun getItemCount() = list.size
}