package com.example.citybikesapp.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.citybikesapp.R
import com.example.citybikesapp.model.entity.Location
import com.example.citybikesapp.model.entity.Station
import com.example.citybikesapp.view.CityDetailFragmentDirections
import com.example.citybikesapp.view.CityListFragmentDirections
import kotlinx.android.synthetic.main.city_list_row.view.*
import kotlinx.android.synthetic.main.station_list_row.view.*

class StationListAdapter: RecyclerView.Adapter<StationListAdapter.StationListHolder>() {
    private var list = emptyList<Station>() //lista location

    class StationListHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationListHolder {
        val view = LayoutInflater.from(parent.context).
            inflate(R.layout.station_list_row, parent, false)

        return StationListHolder(view)
    }

    override fun onBindViewHolder(holder: StationListHolder, position: Int) {
        holder.itemView.nameTextView.text = list[position].name

        holder.itemView.setOnClickListener {
            val action = CityDetailFragmentDirections.actionCityDetailFragmentToStationDetailFragment(list[position], list[position].name)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<Station>){
        this.list = data
        notifyDataSetChanged()
    }
}