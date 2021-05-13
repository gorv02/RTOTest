package com.example.rtotest.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import com.google.android.material.button.MaterialButton

class ExamIntroFragment : Fragment(R.layout.fragment_exam_intro){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val startExamBtn = view.findViewById<MaterialButton>(R.id.start_exam_btn)

        startExamBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_examIntroFragment_to_examFragment
            )
        }
    }
}