package com.example.rtotest.fragments.home_fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R
import com.example.rtotest.adapter.TrafficSignsAdapter
import com.example.rtotest.viewmodels.HomeViewModel


class TrafficSignsFragment : Fragment(R.layout.fragment_traffic_signs) {

    private val mHomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private lateinit var rvTrafficSigns: RecyclerView
    private var position = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        showRvTrafficSigns(view)
    }

    private fun showRvTrafficSigns(view: View) {

        rvTrafficSigns = view.findViewById(R.id.rv_traffic_sign)
        arguments?.getInt("scroll_position")?.let { position = it}

        val adapter = TrafficSignsAdapter()
        mHomeViewModel.listOfTrafficSigns.observe(viewLifecycleOwner){
            adapter.setList(it)
        }

        rvTrafficSigns.apply {
            this.adapter = adapter
            scrollToPosition(position)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_primary, menu)
        menu.findItem(R.id.menu_primary_items).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings_item){
            findNavController().navigate(R.id.action_global_settingsFragment)
            true
        } else super.onOptionsItemSelected(item)
    }
}