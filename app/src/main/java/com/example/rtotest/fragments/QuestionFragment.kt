package com.example.rtotest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rtotest.R
import com.example.rtotest.model.Question

class QuestionFragment() : Fragment() {

    private lateinit var currentQuestion: Question

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
        setHasOptionsMenu(true)

        currentQuestion = arguments?.getParcelable<Question>("currentQuestion")
                ?: Question(1, "", "")

        val queNo = view.findViewById<TextView>(R.id.que_no)
        val que = view.findViewById<TextView>(R.id.que_quefrag)
        val ans = view.findViewById<TextView>(R.id.question_text)

        queNo.text = "Question ${currentQuestion.questionNO}"
        que.text = currentQuestion.que
        ans.text = currentQuestion.ans
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