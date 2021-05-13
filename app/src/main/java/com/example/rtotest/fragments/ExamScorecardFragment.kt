package com.example.rtotest.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rtotest.R
import com.example.rtotest.adapter.ScorecardAdapter
import com.example.rtotest.databinding.FragmentExamScorecardBinding
import com.example.rtotest.viewmodels.ExamViewModel

class ExamScorecardFragment : Fragment() {

    private val binding by lazy{
        FragmentExamScorecardBinding.inflate(layoutInflater) }

    private val mExamViewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(ExamViewModel::class.java)
        } ?: ViewModelProvider(this).get(ExamViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mExamViewModel.setIndex(0)
                    mExamViewModel.resetListOfIsCorrect()
                    mExamViewModel.resetListOfSelectedOptions()
                    findNavController().navigate(R.id.action_scorecardFragment_to_practiceFragment)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_scorecard, menu)

        val scoreMenuItem = menu.findItem(R.id.menu_item_score)
        val correctAns = scoreMenuItem.actionView
            .findViewById<TextView>(R.id.score_menu_correct_text)
        val incorrectAns = scoreMenuItem.actionView
            .findViewById<TextView>(R.id.score_menu_incorrect_text)

        correctAns.text = correctAns().toString()
        incorrectAns.text = incorrectAns().toString()
    }

    private fun correctAns(): Int {
        var i = 0
        mExamViewModel.getListOfIsCorrect().forEach { isCorrect ->
            isCorrect?.let {
                if(it) i++
            }
        }
        return i
    }

    private fun incorrectAns(): Int{
        var i = 0
        mExamViewModel.getListOfIsCorrect().forEach { isCorrect ->
            isCorrect?.let {
                if (!it) i++
            }
        }
        return i
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val linearLM = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.scorecardRv.let { rv ->
            rv.layoutManager = linearLM
            rv.adapter = ScorecardAdapter(
                mExamViewModel.listExamQA,
                mExamViewModel.getListOfIsCorrect(),
                mExamViewModel.getListOfSelectedOptions()
            )
        }
    }
}