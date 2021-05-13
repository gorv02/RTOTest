package com.example.rtotest.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rtotest.R
import com.example.rtotest.adapter.HomeQuestionListAdapter
import com.example.rtotest.adapter.HomeTrafficSignAdapter
import com.example.rtotest.dataGenerator.listQueAns
import com.example.rtotest.dataGenerator.listTrafficIcons
import com.example.rtotest.databinding.FragmentHomeBinding
import com.example.rtotest.model.Question
import com.example.rtotest.model.TrafficSigns


class HomeFragment
    : Fragment(),
        HomeQuestionListAdapter.ClickListener,
        HomeTrafficSignAdapter.ClickListener {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private lateinit var listQA: List<Question>
    private lateinit var listImg: List<TrafficSigns>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRvHorizontal()
        showRvVertical()
    }

    private fun showRvHorizontal() {
        listImg = listTrafficIcons(90)

        val gridLM = GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHorizontal.apply {
            layoutManager = gridLM
            adapter = HomeTrafficSignAdapter(listImg, this@HomeFragment)
        }
    }

    private fun showRvVertical() {
        listQA = listQueAns(300)

        val linearLM = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvVertical.apply {
            layoutManager = linearLM

            adapter = HomeQuestionListAdapter(listQA, this@HomeFragment)
        }
    }

    override fun onClickRvVertical(data: Question, QNo: Int) {

        val action = HomeFragmentDirections.actionHomeFragmentToQuestionFragment(data)
        findNavController().navigate(action)
    }

    override fun onClickRvHorizontal(position: Int) {

        val action = HomeFragmentDirections.actionHomeFragmentToTrafficSigns(position)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_primary, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.settings_item) {
            findNavController().navigate(R.id.action_global_settingsFragment)
            true
        } else super.onOptionsItemSelected(item)
    }
}