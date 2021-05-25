package com.example.citybikesapp.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citybikesapp.R
import com.example.citybikesapp.model.entity.Location
import kotlinx.android.synthetic.main.city_list_row.view.*

class CityListAdapter: RecyclerView.Adapter<CityListAdapter.MyViewHolder>() {
    private var list = emptyList<Location>()

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
        holder.itemView.countryCode.text = list[position].country
        holder.itemView.cityName.text = list[position].city
    }

    fun setData(data: List<Location>){
        this.list = data
        notifyDataSetChanged()
    }
}