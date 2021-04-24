package com.example.rtotest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rtotest.R
import com.example.rtotest.adapter.HorizontalAdapter
import com.example.rtotest.adapter.VerticalAdapter
import com.example.rtotest.dataGenerator.listQueAns
import com.example.rtotest.dataGenerator.listTrafficIcons
import com.example.rtotest.model.Question
import com.example.rtotest.model.TrafficSigns

class HomeFragment() : Fragment(), VerticalAdapter.ClickListener, HorizontalAdapter.ClickListener {

    private lateinit var rvHorizontal: RecyclerView
    private lateinit var rvVertical: RecyclerView
    private lateinit var expandBtn: TextView

    private lateinit var listQA: List<Question>
    private lateinit var listImg: List<TrafficSigns>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandBtn = view.findViewById(R.id.expand_text)

        expandBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_trafficSigns)
        }

        showRvHorizontal(view)
        showRvVertical(view)

    }

    private fun showRvHorizontal(view: View) {
        listImg = listTrafficIcons(40)

        rvHorizontal = view.findViewById(R.id.rv_horizontal)
        val gridLM = GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false)
        rvHorizontal.apply {
            layoutManager = gridLM
            adapter = HorizontalAdapter(listImg, this@HomeFragment)
        }
    }

    private fun showRvVertical(view: View) {
        listQA = listQueAns(50)

        rvVertical = view.findViewById(R.id.rv_vertical)
        val linearLM = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvVertical.apply {
            layoutManager = linearLM

            adapter = VerticalAdapter(listQA, this@HomeFragment)
        }
    }

    override fun onClickRvVertical(data: Question, QNo: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToQuestionFragment(
                QNo, data.que.toString(), data.ans.toString()
        )

        findNavController().navigate(action)
    }

    override fun onClickRvHorizontal(position: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToTrafficSigns(
                position
        )

        findNavController().navigate(action)
    }
}