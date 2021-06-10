package com.example.citybikesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citybikesapp.R
import com.example.citybikesapp.viewmodel.ListsViewModel
import com.example.citybikesapp.viewmodel.adapter.SavedCityAdapter
import kotlinx.android.synthetic.main.fragment_lists.*

class ListsFragment : Fragment() {
    private lateinit var viewModel: ListsViewModel
    private lateinit var listAdapter: SavedCityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ListsViewModel::class.java)
        listAdapter = SavedCityAdapter(viewModel)

        viewModel.loadData()

        viewModel.citiesList.observe(viewLifecycleOwner, Observer {
            listAdapter.list = it
            listAdapter.notifyDataSetChanged()
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}