package com.example.rtotest.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R
import com.example.rtotest.adapter.TrafficSignsAdapter
import com.example.rtotest.dataGenerator.listTrafficIcons
import com.example.rtotest.model.TrafficSigns
import kotlin.properties.Delegates


class TrafficSignsFragment : Fragment() {

    private lateinit var rvTrafficSigns: RecyclerView
    private lateinit var trafficSignList: List<TrafficSigns>
    private var position by Delegates.notNull<Int>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_traffic_signs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        showRvTrafficSigns(view)
    }

    private fun showRvTrafficSigns(view: View) {
        trafficSignList = listTrafficIcons(90)

        rvTrafficSigns = view.findViewById(R.id.rv_traffic_sign)
        this.arguments?.getInt("scroll_position").let { position = it ?: 0}

        val linearLM = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
        )

        rvTrafficSigns.apply {
            layoutManager = linearLM
            adapter = TrafficSignsAdapter(trafficSignList)
            scrollToPosition(position)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        menu.findItem(R.id.share_item).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings_item){
            findNavController().navigate(R.id.action_global_settingsFragment)
            true
        } else super.onOptionsItemSelected(item)
    }

}