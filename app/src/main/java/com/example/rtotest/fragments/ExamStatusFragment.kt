package com.example.rtotest.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import com.example.rtotest.databinding.FragmentExamStatusBinding
import com.example.rtotest.viewmodels.ExamViewModel

class ExamStatusFragment : Fragment() {

    private val binding by lazy {
        FragmentExamStatusBinding.inflate(layoutInflater)
    }
    private val mExamViewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(ExamViewModel::class.java)
        } ?: ViewModelProvider(this).get(ExamViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController()
                                .navigate(R.id.action_examStatusFragment_to_practiceFragment)
                    }
                }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (correctAns() >= 6) {
            binding.examStatusTv.text = "Passed"
            binding.examStatusTv.setTextColor(Color.GREEN)
        } else {
            binding.examStatusTv.text = "Failed"
            binding.examStatusTv.setTextColor(Color.RED)
        }

        binding.examStatusHomeBtn.setOnClickListener {
            mExamViewModel.setIndex(0)
            mExamViewModel.resetListOfIsCorrect()
            mExamViewModel.resetListOfSelectedOptions()
            findNavController().navigate(R.id.action_examStatusFragment_to_practiceFragment)
        }

        binding.examStatusScorecardBtn.setOnClickListener {
            findNavController().navigate(R.id.action_examStatusFragment_to_scorecardFragment)
        }
    }

    private fun correctAns(): Int {
        var i = 0
        mExamViewModel.getListOfIsCorrect().forEach { isCorrect ->
            isCorrect?.let {
                if (it) i++
            }
        }
        return i
    }
}