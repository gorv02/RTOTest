package com.example.rtotest.fragments.exam_fragments

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
import com.example.rtotest.viewmodels.ExamViewModel
import kotlinx.android.synthetic.main.fragment_exam_status.*

class ExamStatusFragment : Fragment() {

    private val mExamViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ExamViewModel::class.java)
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
        return inflater.inflate(R.layout.fragment_exam_status, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (correctAns() >= 6) {
            exam_status_tv.text = "Passed"
            exam_status_tv.setTextColor(Color.GREEN)
        } else {
            exam_status_tv.text = "Failed"
            exam_status_tv.setTextColor(Color.RED)
        }

        exam_status_home_btn.setOnClickListener {
            mExamViewModel.setIndex(0)
            mExamViewModel.resetListOfIsCorrect()
            mExamViewModel.resetListOfSelectedOptions()
            findNavController().navigate(R.id.action_examStatusFragment_to_practiceFragment)
        }

        exam_status_scorecard_btn.setOnClickListener {
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