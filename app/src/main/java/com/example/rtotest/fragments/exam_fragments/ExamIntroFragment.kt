package com.example.rtotest.fragments.exam_fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import kotlinx.android.synthetic.main.fragment_exam_intro.*

class ExamIntroFragment : Fragment(R.layout.fragment_exam_intro){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        start_exam_btn.setOnClickListener {
            findNavController().navigate(
                R.id.action_examIntroFragment_to_examFragment
            )
        }
    }
}