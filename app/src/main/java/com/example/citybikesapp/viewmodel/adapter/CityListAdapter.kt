package com.example.citybikesapp.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.citybikesapp.R
import com.example.citybikesapp.model.entity.Location
import com.example.citybikesapp.view.CityListFragmentDirections
import kotlinx.android.synthetic.main.city_list_row.view.*

class CityListAdapter: RecyclerView.Adapter<CityListAdapter.MyViewHolder>() {
    private var list = emptyList<Location>() //lista location

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(LayoutInflater.from(parent.context).
                inflate(R.layout.city_list_row, parent, false))

        return view
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //przypisanie danych
        holder.itemView.countryCode.text = list[position].country
        holder.itemView.cityName.text = list[position].city

        //kliknięcie miasta
        holder.itemView.setOnClickListener {
            val action = CityListFragmentDirections.actionCityListFragmentToCityDetailFragment(list[position], list[position].city)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(data: List<Location>){
        this.list = data //ustawienie listy location
        notifyDataSetChanged()
    }
}