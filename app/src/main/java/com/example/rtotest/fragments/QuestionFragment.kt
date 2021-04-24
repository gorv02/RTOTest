package com.example.rtotest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.rtotest.R

class QuestionFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val qNo = this.arguments?.getInt("Qno")
        val question = this.arguments?.getString("question")
        val answer = this.arguments?.getString("answer")

        val queNo = view.findViewById<TextView>(R.id.que_no)
        val que = view.findViewById<TextView>(R.id.que_quefrag)
        val ans = view.findViewById<TextView>(R.id.question_text)

        queNo.text = "Question $qNo"
        que.text = question
        ans.text = answer
    }
}